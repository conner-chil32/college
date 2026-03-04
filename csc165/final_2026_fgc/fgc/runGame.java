package fgc;

import org.joml.Vector3f;
import tage.*;
import tage.input.InputManager;

public class runGame extends VariableFrameRateGame {

    private static Engine engine;

    private boolean paused=false;
    private double lastFrameTime, currFrameTime, elapsTime;
    private InputManager im;

    private Player player1;

    public runGame() { super(); }

    public static void main(String[] args)
    {	runGame game = new runGame();
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

        player1 = new Player(im);
        player1.initalizeControls();

    }

    @Override
    public void update() {
        lastFrameTime = currFrameTime;
        currFrameTime = System.currentTimeMillis();
        if (!paused) elapsTime += (currFrameTime - lastFrameTime) / 1000.0;

        im.update((float) elapsTime);
        player1.update((float) elapsTime);

    }

    @Override
    public void shutdown() {
        super.shutdown();
    }
}
