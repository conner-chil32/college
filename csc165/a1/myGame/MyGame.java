package myGame;

import myGame.gameActions.*;
import myGame.manualModels.dolHouse;
import net.java.games.input.Component;
import org.joml.*;
import tage.*;
import tage.input.IInputManager;
import tage.input.InputManager;
import tage.physics.PhysicsEngine;
import tage.physics.PhysicsObject;
import tage.shapes.*;

import java.awt.*;
import java.lang.Math;
import java.util.*;

public class MyGame extends VariableFrameRateGame
{
    private static Engine engine;

    public boolean paused = false;
    private double lastFrameTime, currFrameTime;
    public double elapsTime;

    public boolean isOnDolphin = true;

    public MyGame() { super(); }

    public GameObject dol;
    private ObjShape dolS;
    private TextureImage dolTx;
    private Light light1, light2, light3;
    public Camera cam;

    private InputManager im;
    private GameObject x, y, z;
    private ObjShape linxS, linyS, linzS;

    public GameObject pln1, pln2, pln3, house;
    private ObjShape pln1S, pln2S, pln3S, houseS;
    private TextureImage pln1Tx, pln2Tx, pln3Tx, houseTx;

    public GameObject img1, img2, img3, img4, img5, img6;
    private ObjShape img1S, img2S, img3S, img4S, img5S, img6S;
    private TextureImage imgDefTx;

    public GameObject closestPlanet;
    public ArrayList<GameObject> images = new ArrayList<GameObject>();
    public ArrayList<TextureImage> imageTextures = new ArrayList<TextureImage>();

    public ArrayList<GameObject> planets = new ArrayList<>();

    public int score = 0;
    private boolean collided = false;

    public static void main(String[] args) {
        MyGame game = new MyGame();
        engine = new Engine(game);
        engine.initializeSystem();
        game.buildGame();
        game.startGame();
    }
    @Override
    public void loadShapes() {
        dolS = new ImportedModel("dolphinHighPoly.obj");

        linxS = new Line(new Vector3f(0f,0f,0f), new Vector3f(3f,0f,0f));
        linyS = new Line(new Vector3f(0f,0f,0f), new Vector3f(0f,3f,0f));
        linzS = new Line(new Vector3f(0f,0f,0f), new Vector3f(0f,0f,-3f));

        pln1S = new Sphere();
        pln2S = new Sphere();
        pln3S = new Sphere();

        img1S = new Cube();
        img2S = new Cube();
        img3S = new Cube();
        img4S = new Cube();
        img5S = new Cube();
        img6S = new Cube();

        houseS = new dolHouse();
    }

    @Override
    public void loadTextures() {
        dolTx = new TextureImage("Dolphin_HighPolyUV.jpg");
        pln1Tx = new TextureImage("2k_mars.jpg");
        pln2Tx = new TextureImage("2k_mercury.jpg");
        pln3Tx = new TextureImage("2k_jupiter.jpg");
        imgDefTx = new TextureImage("TextureLabs_Grunge_138S.jpg");
        houseTx = new TextureImage("Texturelabs_Wood_189S.jpg");
    }

    @Override
    public void buildObjects() {
        Matrix4f initialTranslation, initialScale;

        dol = new GameObject(GameObject.root(), dolS, dolTx);
        initialTranslation = (new Matrix4f()).translation(0,0,0);
        initialScale = (new Matrix4f()).scaling(3.0f);
        dol.setLocalTranslation(initialTranslation);
        dol.setLocalScale(initialScale);

        img1 = new GameObject(dol, img1S, imgDefTx);
        initialScale = (new Matrix4f()).scaling(0.05f);
        img1.setLocalTranslation(initialTranslation);
        img1.setLocalScale(initialScale);
        img1.setLocalLocation(dol.getLocalUpVector().mul(2.0f).add(dol.getLocalRightVector().mul(1.4f)));
        images.add(img1);
        imageTextures.add(img1.getTextureImage());

        img2 = new GameObject(dol, img2S, imgDefTx);
        initialScale = (new Matrix4f()).scaling(0.05f);
        img2.setLocalTranslation(initialTranslation);
        img2.setLocalScale(initialScale);
        img1.setLocalLocation(dol.getLocalUpVector().mul(1.5f).add(dol.getLocalRightVector().mul(1.4f)));
        images.add(img2);
        imageTextures.add(img2.getTextureImage());

        img3 = new GameObject(dol, img3S, imgDefTx);
        initialScale = (new Matrix4f()).scaling(0.05f);
        img3.setLocalTranslation(initialTranslation);
        img3.setLocalScale(initialScale);
        img1.setLocalLocation(dol.getLocalUpVector().mul(1.0f).add(dol.getLocalRightVector().mul(1.4f)));
        images.add(img3);
        imageTextures.add(img3.getTextureImage());


        x = new GameObject(GameObject.root(), linxS);
        y = new GameObject(GameObject.root(), linyS);
        z = new GameObject(GameObject.root(), linzS);
        (x.getRenderStates()).setColor(new Vector3f(1f,0f,0f));
        (y.getRenderStates()).setColor(new Vector3f(0f,1f,0f));
        (z.getRenderStates()).setColor(new Vector3f(0f,0f,1f));

        pln1 = new GameObject(GameObject.root(), pln1S, pln1Tx);
        initialTranslation = (new Matrix4f().translation(20.0f, -2.0f, 10.3f));
        initialScale = (new Matrix4f().scale(1.2f));
        pln1.setLocalTranslation(initialTranslation);
        pln1.setLocalScale(initialScale);
        planets.add(pln1);

        pln2 = new GameObject(GameObject.root(), pln2S, pln2Tx);
        initialTranslation = (new Matrix4f().translation(10.2f, 23.0f, 5.0f));
        initialScale = (new Matrix4f().scale(2.0f));
        pln2.setLocalTranslation(initialTranslation);
        pln2.setLocalScale(initialScale);
        planets.add(pln2);

        pln3 = new GameObject(GameObject.root(), pln3S, pln3Tx);
        initialTranslation = (new Matrix4f().translation(-5.0f, 6.0f, 6.3f));
        initialScale = (new Matrix4f().scale(2.3f));
        pln3.setLocalTranslation(initialTranslation);
        pln3.setLocalScale(initialScale);
        planets.add(pln3);

        house = new GameObject(GameObject.root(), houseS, houseTx);
        initialTranslation = (new Matrix4f().translation(16f, 0.0f, 20f));
        initialScale = (new Matrix4f().scale(1.5f));
        house.setLocalTranslation(initialTranslation);
        house.setLocalScale(initialScale);

        img4 = new GameObject(house, img4S, imgDefTx);
        initialScale = (new Matrix4f()).scaling(0.2f);
        initialTranslation = (new Matrix4f().translation(0.0f, 0.0f, 0.0f));
        img4.setLocalTranslation(initialTranslation);
        img4.setLocalScale(initialScale);
        img4.setLocalLocation(house.getLocalUpVector().mul(2.0f).add(house.getLocalForwardVector().mul(2.0f)).add(house.getLocalRightVector().mul(2.0f)));

        img5 = new GameObject(house, img5S, imgDefTx);
        initialScale = (new Matrix4f()).scaling(0.2f);
        initialTranslation = (new Matrix4f().translation(0.0f, 0.0f, 0.0f));
        img5.setLocalTranslation(initialTranslation);
        img5.setLocalScale(initialScale);
        img5.setLocalLocation(house.getLocalUpVector().mul(2.0f).add(house.getLocalForwardVector().mul(2.0f)));

        img6 = new GameObject(house, img6S, imgDefTx);
        initialTranslation = (new Matrix4f().translation(0.0f, 0.0f, 0.0f));
        initialScale = (new Matrix4f()).scaling(0.2f);
        img6.setLocalTranslation(initialTranslation);
        img6.setLocalScale(initialScale);
        img6.setLocalLocation(house.getLocalUpVector().mul(2.0f).add(house.getLocalForwardVector().mul(2.0f)).add(house.getLocalRightVector().mul(-2.0f)));
    }

    @Override
    public void initializeLights() {
        Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);

        light1 = new Light();
        light1.setLocation(pln1.getWorldUpVector().mul(1.3f));
        (engine.getSceneGraph()).addLight(light1);

        light2 = new Light();
        light2.setLocation(pln2.getWorldUpVector().mul(2.0f));
        (engine.getSceneGraph()).addLight(light2);

        light3 = new Light();
        light3.setLocation(pln3.getWorldUpVector().mul(4.0f));
        (engine.getSceneGraph()).addLight(light3);
    }

    @Override
    public void initializeGame() {
        cam = (engine.getRenderSystem()
                .getViewport("MAIN").getCamera());

        lastFrameTime = System.currentTimeMillis();
        currFrameTime = System.currentTimeMillis();
        elapsTime = 0.0;
        cam = (engine.getRenderSystem().getViewport("MAIN").getCamera());
        cam.setLocation(new Vector3f(0,0,5));
        (engine.getRenderSystem()).setWindowDimensions(1900,1000);


        im = engine.getInputManager();

        FwdAction fwdAction = new FwdAction(this, false);
        BckAction bckAction = new BckAction(this, false);

        EnterDolphin dolphinToggle = new EnterDolphin(this, (engine.getHUDmanager()));

        yawAction yawLeft = new yawAction(this, false, "LEFT");
        yawAction yawRight = new yawAction(this, false, "RIGHT");
        pitchAction pitchUp = new pitchAction(this, false, "UP");
        pitchAction pitchDown = new pitchAction(this, false, "DOWN");

        yawAction conYaw = new yawAction(this, true, "");
        FwdAction conMov = new FwdAction(this, true);

        TakePicture takePicture = new TakePicture(this, (engine.getHUDmanager()));

        im.associateActionWithAllKeyboards(Component.Identifier.Key.SPACE, dolphinToggle, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.W, fwdAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.S, bckAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.A, yawLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.D, yawRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.UP, pitchUp, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.DOWN, pitchDown, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.P, takePicture, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);

        im.associateActionWithAllGamepads(Component.Identifier.Axis.X, conYaw,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllGamepads(Component.Identifier.Axis.Y, conMov, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        setCameraOnDolphin(cam);
    }

    @Override
    public void update() {
        lastFrameTime = currFrameTime;
        currFrameTime = System.currentTimeMillis();

        if (!paused) elapsTime += (currFrameTime - lastFrameTime) / 1000.0;

        im.update((float) elapsTime);

        if(isOnDolphin) {
            setCameraOnDolphin(cam);
        }

        spinPlanet(pln1, (float)elapsTime, false, 1f);
        spinPlanet(pln2, (float)elapsTime, true, 0.4f);
        spinPlanet(pln3, (float)elapsTime, true, 2.3f);

        checkCollision();

        Vector3f hud1Color = new Vector3f(0,0,1);
        (engine.getHUDmanager()).setHUD1("Score: " + score, hud1Color, 15, 15);

        if(!paused) {
            hud1Color = new Vector3f(0,0,1);
            (engine.getHUDmanager()).setHUD1("Score: " + score, hud1Color, 15, 15);
        }

        if(paused && collided) {
            hud1Color = new Vector3f(1,0,0);
            (engine.getHUDmanager()).setHUD2("", hud1Color, 15, 15);
            (engine.getHUDmanager()).setHUD1("GAME OVER", hud1Color, 15, 15);
        }

    }

    public void setCameraOnDolphin(Camera cam) {
        Vector3f loc, fwd, up, right;
        loc = dol.getWorldLocation();
        fwd = dol.getWorldForwardVector();
        up = dol.getWorldUpVector();
        right = dol.getWorldRightVector();
        cam.setU(right);
        cam.setV(up);
        cam.setN(fwd);
        cam.setLocation(loc.add(up.mul(1.3f)).add(fwd.mul(-2.5f)));
    }

    public void spinPlanet(GameObject planet, float elapsTime, boolean inverted, float rotationFactor) {
        if(inverted) planet.setLocalRotation(new Matrix4f().rotation(elapsTime * rotationFactor,0,1,0).invert());
        else planet.setLocalRotation(new Matrix4f().rotation(elapsTime,0,1,0));
    }

    public void checkCollision() {
        double distance;
        for(GameObject planet: planets) {
            float x = dol.getWorldLocation().x - planet.getWorldLocation().x;
            float y = dol.getWorldLocation().y - planet.getWorldLocation().y;
            float z = dol.getWorldLocation().z - planet.getWorldLocation().z;
            distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y,2) + Math.pow(z,2));

            if(distance < 6f){
                closestPlanet = planet;
            }

            if(distance <= 2f) {
                paused = true;
                collided = true;
            }
        }
    }

    public double getDistanceFromDolphinToHome() {
        float x = dol.getWorldLocation().x - house.getWorldLocation().x;
        float y = dol.getWorldLocation().y - house.getWorldLocation().y;
        float z = dol.getWorldLocation().z - house.getWorldLocation().z;
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y,2) + Math.pow(z,2));
    }
}
