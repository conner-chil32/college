package fgc.Scene.GameScenes;

import fgc.Scene.GameScene;
import fgc.Scene.SceneElement;
import fgc.runGame;
import org.joml.Matrix4f;
import tage.Engine;
import tage.GameObject;
import tage.TextureImage;
import tage.shapes.*;

public class MainMenu extends GameScene {
    private Engine engine;

    public MainMenu() {
        name = "Main Menu";
        sceneElements.add(new SceneElement());
    }

    @Override
    public void loadModels() {
        sceneElements.get(0).setModel(new Plane());

        if(runGame.debug) System.out.println("Loaded Models from Main Menu Scene");
    }

    @Override
    public void loadTextures() {
        sceneElements.get(0).setTexture(new TextureImage("test_main_menu.png"));

        if(runGame.debug) System.out.println("Loaded Textures from Main Menu Scene");
    }

    @Override
    public void createGameObjects() {
        sceneElements.get(0).object = new GameObject(
                GameObject.root(),
                sceneElements.get(0).model,
                sceneElements.get(0).texture
        );

        sceneElements.get(0).object.setLocalRotation(new Matrix4f().rotate(1.57f, 1,0,0));
        sceneElements.get(0).object.setLocalScale(new Matrix4f().scale(1f,1f,1f));

        unloadGameObjects();
    }

    @Override
    public void loadGameObjects() {
        for(SceneElement element: sceneElements) {
            element.object.getRenderStates().enableRendering();
        }
    }

    @Override
    public void unloadGameObjects() {
        for(SceneElement element: sceneElements) {
            element.object.getRenderStates().disableRendering();
        }
    }
}
