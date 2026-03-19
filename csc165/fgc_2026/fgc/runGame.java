package fgc;

import fgc.Scene.SceneElement;
import fgc.Scene.SceneManager;
import tage.*;
import tage.input.InputManager;
import tage.shapes.*;

import java.lang.Math;
import java.awt.event.*;
import java.util.ArrayList;

import org.joml.*;

public class runGame extends VariableFrameRateGame
{
	private static Engine engine;

	private boolean paused=false;
	private double lastFrameTime, currFrameTime, elapsTime;
	private InputManager im;

	public static boolean debug = true;

	private SceneManager sm = new SceneManager();

//	private GameObject dol;
//	private ObjShape dolS;
//	private TextureImage doltx;
	private Light light1;

	public runGame() { super(); }

	public static void main(String[] args)
	{	runGame game = new runGame();
		engine = new Engine(game);
		engine.initializeSystem();
		game.buildGame();
		game.startGame();
	}

	@Override
	public void loadShapes()
	{
//		dolS = new ImportedModel("dolphinHighPoly.obj");
		sm.loadObjShapes();
	}

	@Override
	public void loadTextures()
	{
		// doltx = new TextureImage("Dolphin_HighPolyUV.jpg");
		sm.loadTextures();
	}

	@Override
	public void buildObjects()
	{
//		Matrix4f initialTranslation, initialScale;
//
//		// build dolphin in the center of the window
//		dol = new GameObject(GameObject.root(), dolS, doltx);
//		initialTranslation = (new Matrix4f()).translation(0,0,0);
//		initialScale = (new Matrix4f()).scaling(3.0f);
//		dol.setLocalTranslation(initialTranslation);
//		dol.setLocalScale(initialScale);
		sm.loadObjects();
	}

	@Override
	public void initializeLights()
	{
		Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);
		light1 = new Light();
		light1.setLocation(new Vector3f(5.0f, 4.0f, 2.0f));
		(engine.getSceneGraph()).addLight(light1);
	}

	@Override
	public void initializeGame()
	{
		im = engine.getInputManager();
		sm.changeScene(sm.scenes.get(0));

		lastFrameTime = System.currentTimeMillis();
		currFrameTime = System.currentTimeMillis();
		elapsTime = 0.0;

		(engine.getRenderSystem()).setWindowDimensions(1900,1000);

		// ------------- positioning the camera -------------
		(engine.getRenderSystem().getViewport("MAIN").getCamera()).setLocation(new Vector3f(0,0,5));
	}

	@Override
	public void update()
	{	// rotate dolphin if not paused
		lastFrameTime = currFrameTime;
		currFrameTime = System.currentTimeMillis();
		if (!paused) elapsTime += (currFrameTime - lastFrameTime) / 1000.0;

		im.update((float) elapsTime);



//		dol.setLocalRotation((new Matrix4f()).rotation((float)elapsTime, 0, 1, 0));

		// build and set HUD
//		int elapsTimeSec = Math.round((float)elapsTime);
//		String elapsTimeStr = Integer.toString(elapsTimeSec);
//		String counterStr = Integer.toString(counter);
//		String dispStr1 = "Time = " + elapsTimeStr;
//		String dispStr2 = "Keyboard hits = " + counterStr;
//		Vector3f hud1Color = new Vector3f(1,0,0);
//		Vector3f hud2Color = new Vector3f(0,0,1);
//		(engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, 15, 15);
//		(engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, 500, 15);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if(e.getKeyCode() == KeyEvent.VK_A) {
			for(SceneElement sc: SceneManager.currentScene.sceneElements) {
				sc.object.setLocalRotation(new Matrix4f().rotation(-(float) elapsTime, 0f,1f,0f));
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			sm.changeScene(sm.scenes.get(1));
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			sm.changeScene(sm.scenes.get(0));
		}
	}

	@Override
	public void shutdown() {
		super.shutdown();
	}
}