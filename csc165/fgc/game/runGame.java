package game;

import game.Scenes.GameScene;
import game.Scenes.MainMenu;
import org.joml.Vector3f;
import tage.Engine;
import tage.Light;
import tage.VariableFrameRateGame;
import tage.input.InputManager;

public class runGame extends VariableFrameRateGame {

    private static Engine engine;
    private static runGame game;
    private boolean paused=false;
    private double lastFrameTime, currFrameTime, elapsTime;
    private InputManager im;

    public static boolean debug = true;
    public static GameScene currentScene;

    private Light light1;

    public runGame() { super(); }

    public static void main(String[] args) {
        game = new runGame();
        engine = new Engine(game);
        engine.initializeSystem();
        game.buildGame();
        game.startGame();
    }


    @Override
    public void loadShapes() {

    }

    @Override
    public void loadTextures() {

    }

    @Override
    public void buildObjects() {

    }

    @Override
    public void initializeLights() {
        Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);
        light1 = new Light();
        light1.setLocation(new Vector3f(5.0f, 4.0f, 2.0f));
        (engine.getSceneGraph()).addLight(light1);
    }

    @Override
    public void initializeGame() {
        im = engine.getInputManager();
        lastFrameTime = System.currentTimeMillis();
        currFrameTime = System.currentTimeMillis();
        elapsTime = 0.0;

        (engine.getRenderSystem()).setWindowDimensions(1900,1000);

        // ------------- positioning the camera -------------
        (engine.getRenderSystem().getViewport("MAIN").getCamera()).setLocation(new Vector3f(0,0,5));

        currentScene = new MainMenu();
        currentScene.loadSceneElements();

        System.out.println(engine.getSceneGraph().getNumGameObjects());
    }

    @Override
    public void update() {
        lastFrameTime = currFrameTime;
        currFrameTime = System.currentTimeMillis();
        if (!paused) elapsTime += (currFrameTime - lastFrameTime) / 1000.0;

        im.update((float) elapsTime);
    }

    @Override
    public void startGame() {
        super.startGame();
        if(debug) System.out.println("Game Running");
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }
}
