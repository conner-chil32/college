package a2;

import a2.gameActions.*;
import a2.manualModels.Pyramid;
import a2.manualModels.dolHouse;
import net.java.games.input.Component;
import org.joml.*;
import org.w3c.dom.Text;
import tage.*;
import tage.input.IInputManager;
import tage.input.InputManager;
import tage.nodeControllers.RotationController;
import tage.nodeControllers.TextureController;
import tage.physics.PhysicsEngine;
import tage.physics.PhysicsObject;
import tage.shapes.*;

import java.awt.*;
import java.awt.event.KeyEvent;
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
    private Light light1, light2, light3, light4;
    public Camera cam;
    public Camera topDownCam;
    private CameraOrbit3D orbitCam;

    private InputManager im;
    private GameObject x, y, z;
    private ObjShape linxS, linyS, linzS;

    private GameObject floor;
    private ObjShape floorS;
    private TextureImage floorTx;

    public RotationController rc;
    public TextureController tc;

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
    public void createViewports() {
        (engine.getRenderSystem()).addViewport("LEFT",0,0,1f,1f);
        (engine.getRenderSystem()).addViewport("RIGHT",.75f,0,.25f,.25f);

        Viewport leftVp = (engine.getRenderSystem()).getViewport("LEFT");
        Viewport rightVp =
                (engine.getRenderSystem()).getViewport("RIGHT");
        Camera leftCamera = leftVp.getCamera();
        Camera rightCamera = rightVp.getCamera();

        rightVp.setHasBorder(true);
        rightVp.setBorderWidth(4);
        rightVp.setBorderColor(0.0f, 1.0f, 0.0f);

        leftCamera.setLocation(new Vector3f(-2,0,2));
        leftCamera.setU(new Vector3f(1,0,0));
        leftCamera.setV(new Vector3f(0,1,0));
        leftCamera.setN(new Vector3f(0,0,-1));
        rightCamera.setLocation(new Vector3f(0,10,0));
        rightCamera.setU(new Vector3f(1,0,0));
        rightCamera.setV(new Vector3f(0,0,-1));
        rightCamera.setN(new Vector3f(0,-1,0));
    }

    @Override
    public void loadShapes() {
        dolS = new ImportedModel("dolphinHighPoly.obj");

        linxS = new Line(new Vector3f(0f,0f,0f), new Vector3f(3f,0f,0f));
        linyS = new Line(new Vector3f(0f,0f,0f), new Vector3f(0f,3f,0f));
        linzS = new Line(new Vector3f(0f,0f,0f), new Vector3f(0f,0f,-3f));

        floorS = new Plane();

        pln1S = new Pyramid();
        pln2S = new Pyramid();
        pln3S = new Pyramid();

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
        floorTx = new TextureImage("sand.jpg");

        pln1Tx = new TextureImage("brick1.jpg");
        pln2Tx = new TextureImage("ice.jpg");
        pln3Tx = new TextureImage("metal.jpg");

        houseTx = new TextureImage("wood.jpg");
        imgDefTx = new TextureImage("paper.jpg");
    }

    @Override
    public void buildObjects() {
        Matrix4f initialTranslation, initialScale;

        dol = new GameObject(GameObject.root(), dolS, dolTx);
        initialTranslation = (new Matrix4f()).translation(0,1.5f,0);
        initialScale = (new Matrix4f()).scaling(3.0f);
        dol.setLocalTranslation(initialTranslation);
        dol.setLocalScale(initialScale);

        img1 = new GameObject(dol, img1S, imgDefTx);
        initialScale = (new Matrix4f()).scaling(0.05f);
        img1.setLocalTranslation(initialTranslation);
        img1.setLocalScale(initialScale);
        images.add(img1);
        imageTextures.add(img1.getTextureImage());


        img2 = new GameObject(dol, img2S, imgDefTx);
        initialScale = (new Matrix4f()).scaling(0.05f);
        img2.setLocalTranslation(initialTranslation);
        img2.setLocalScale(initialScale);
        images.add(img2);
        imageTextures.add(img2.getTextureImage());

        img3 = new GameObject(dol, img3S, imgDefTx);
        initialScale = (new Matrix4f()).scaling(0.05f);
        img3.setLocalTranslation(initialTranslation);
        img3.setLocalScale(initialScale);
        images.add(img3);
        imageTextures.add(img3.getTextureImage());

        floor = new GameObject(GameObject.root(), floorS, floorTx);
        initialTranslation = (new Matrix4f().translation(0,0f,0));
        initialScale = (new Matrix4f().scale(100,1,100));
        floor.setLocalTranslation(initialTranslation);
        floor.setLocalScale(initialScale);

        x = new GameObject(GameObject.root(), linxS);
        y = new GameObject(GameObject.root(), linyS);
        z = new GameObject(GameObject.root(), linzS);
        (x.getRenderStates()).setColor(new Vector3f(1f,0f,0f));
        (y.getRenderStates()).setColor(new Vector3f(0f,1f,0f));
        (z.getRenderStates()).setColor(new Vector3f(0f,0f,1f));

        pln1 = new GameObject(GameObject.root(), pln1S, pln1Tx);
        initialTranslation = (new Matrix4f().translation(20.0f, 0, 10f));
        initialScale = (new Matrix4f().scale(10.0f));
        pln1.setLocalTranslation(initialTranslation);
        pln1.setLocalScale(initialScale);
        planets.add(pln1);

        pln2 = new GameObject(GameObject.root(), pln2S, pln2Tx);
        initialTranslation = (new Matrix4f().translation(-50f, 0, 20f));
        initialScale = (new Matrix4f().scale(10f));
        pln2.setLocalTranslation(initialTranslation);
        pln2.setLocalScale(initialScale);
        planets.add(pln2);

        pln3 = new GameObject(GameObject.root(), pln3S, pln3Tx);
        initialTranslation = (new Matrix4f().translation(-30.0f, 0, -30f));
        initialScale = (new Matrix4f().scale(10f));
        pln3.setLocalTranslation(initialTranslation);
        pln3.setLocalScale(initialScale);
        planets.add(pln3);

        house = new GameObject(GameObject.root(), houseS, houseTx);
        initialTranslation = (new Matrix4f().translation(10f, 0.0f, -50f));
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

        light1 = new Light();
        light1.setLocation(pln1.getWorldUpVector().mul(2.0f));
        (engine.getSceneGraph()).addLight(light1);

        light2 = new Light();
        light2.setLocation(pln2.getWorldUpVector().mul(2.0f));
        (engine.getSceneGraph()).addLight(light2);

        light3 = new Light();
        light3.setLocation(pln3.getWorldUpVector().mul(2.0f));
        (engine.getSceneGraph()).addLight(light3);

        light4 = new Light();
        light4.setLocation(house.getWorldUpVector().mul(2.0f));
        (engine.getSceneGraph()).addLight(light4);
    }

    @Override
    public void initializeGame() {
        cam = (engine.getRenderSystem()
                .getViewport("LEFT").getCamera());
        topDownCam = (engine.getRenderSystem().getViewport("RIGHT").getCamera());

        orbitCam = new CameraOrbit3D(cam, dol, engine, this);

        rc = new RotationController(engine, new Vector3f(0,1f,0), 0.001f);
        tc = new TextureController(engine, new TextureImage("grass.jpg"));

        (engine.getSceneGraph()).addNodeController(rc);
        (engine.getSceneGraph()).addNodeController(tc);

        tc.addTarget(floor);

        rc.toggle();

        lastFrameTime = System.currentTimeMillis();
        currFrameTime = System.currentTimeMillis();
        elapsTime = 0.0;

        (engine.getRenderSystem()).setWindowDimensions(1900,1000);

        im = engine.getInputManager();

        FwdAction fwdAction = new FwdAction(this, false);
        BckAction bckAction = new BckAction(this, false);

        yawAction yawLeft = new yawAction(this, false, "LEFT");
        yawAction yawRight = new yawAction(this, false, "RIGHT");

        yawAction conYaw = new yawAction(this, true, "");
        FwdAction conMov = new FwdAction(this, true);

        CheckIfGameOver endGame = new CheckIfGameOver(this);

        TakePicture takePicture = new TakePicture(this, (engine.getHUDmanager()));

        im.associateActionWithAllKeyboards(Component.Identifier.Key.W, fwdAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.S, bckAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.A, yawLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.D, yawRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        im.associateActionWithAllKeyboards(Component.Identifier.Key.P, takePicture, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.SPACE, endGame, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);

        im.associateActionWithAllGamepads(Component.Identifier.Axis.X, conYaw,IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllGamepads(Component.Identifier.Axis.Y, conMov, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllGamepads(Component.Identifier.Button.B, takePicture, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        im.associateActionWithAllGamepads(Component.Identifier.Button.A, endGame, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);

    }

    @Override
    public void update() {
        orbitCam.updateCameraPosition();
        lastFrameTime = currFrameTime;
        currFrameTime = System.currentTimeMillis();

        if (!paused) elapsTime += (currFrameTime - lastFrameTime) / 1000.0;

        im.update((float) elapsTime);

        checkCollision();
        rotateImages();

        Vector3f hud1Color = new Vector3f(0,0,1);
        (engine.getHUDmanager()).setHUD1("Score: " + score, hud1Color, 15, 15);

        if(!paused) {
            hud1Color = new Vector3f(0,0,1);
            (engine.getHUDmanager()).setHUD1("Score: " + score, hud1Color, 15, 15);
            (engine.getHUDmanager()).setHUD2(dol.getWorldLocation().x + ", " + dol.getWorldLocation().y + ", " + dol.getWorldLocation().z, hud1Color, 1500, 15);
        }

        if(paused && collided) {
            hud1Color = new Vector3f(1,0,0);
            (engine.getHUDmanager()).setHUD1("GAME OVER", hud1Color, 15, 15);
        }

    }


    public void checkCollision() {
        double distance;
        for(GameObject planet: planets) {
            float x = dol.getWorldLocation().x - planet.getWorldLocation().x;
            float y = dol.getWorldLocation().y - planet.getWorldLocation().y;
            float z = dol.getWorldLocation().z - planet.getWorldLocation().z;
            distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y,2) + Math.pow(z,2));

            if(distance < 15f){
                closestPlanet = planet;
            }

            if(distance <= 10f) {
                paused = true;
                collided = true;
            }
        }
    }

    public void checkIfGameOver() {
        if(!imageTextures.contains(imgDefTx)) {
            if(getDistanceFromDolphinToHome() < 6f) {
                img4.setTextureImage(imageTextures.get(0));
                img5.setTextureImage(imageTextures.get(1));
                img6.setTextureImage(imageTextures.get(2));

                paused = true;

                Vector3f hud1Color = new Vector3f(1,0,0);
                (engine.getHUDmanager()).setHUD1("YOU WIN", hud1Color, 15, 15);

                tc.toggle();
            }
        }
    }

    public double getDistanceFromDolphinToHome() {
        float x = dol.getWorldLocation().x - house.getWorldLocation().x;
        float y = dol.getWorldLocation().y - house.getWorldLocation().y;
        float z = dol.getWorldLocation().z - house.getWorldLocation().z;
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y,2) + Math.pow(z,2));
    }

    public void rotateImages() {
        img1.setLocalLocation(dol.getLocalUpVector().mul(1.2f));
        img1.setLocalLocation(dol.getLocalRightVector().mul(.9f));

        img2.setLocalLocation(dol.getLocalUpVector().mul(1.2f));
        img2.setLocalLocation(dol.getLocalRightVector().mul(1.3f));

        img3.setLocalLocation(dol.getLocalUpVector().mul(1.2f));
        img3.setLocalLocation(dol.getLocalRightVector().mul(1.7f));
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_I:
                topDownCam.setLocation(new Vector3f(topDownCam.getLocation().x, topDownCam.getLocation().y,topDownCam.getLocation().z - 1f));
                break;
            case KeyEvent.VK_K:
                topDownCam.setLocation(new Vector3f(topDownCam.getLocation().x, topDownCam.getLocation().y,topDownCam.getLocation().z + 1f));
                break;
            case KeyEvent.VK_J:
                topDownCam.setLocation(new Vector3f(topDownCam.getLocation().x - 1f, topDownCam.getLocation().y,topDownCam.getLocation().z));
                break;
            case KeyEvent.VK_L:
                topDownCam.setLocation(new Vector3f(topDownCam.getLocation().x + 1f, topDownCam.getLocation().y,topDownCam.getLocation().z));
                break;
            case KeyEvent.VK_9:
                topDownCam.setLocation(new Vector3f(topDownCam.getLocation().x, topDownCam.getLocation().y + 1f ,topDownCam.getLocation().z));
                break;
            case KeyEvent.VK_0:
                topDownCam.setLocation(new Vector3f(topDownCam.getLocation().x, topDownCam.getLocation().y - 1f ,topDownCam.getLocation().z));
                break;
        }

        super.keyPressed(e);
    }
}
