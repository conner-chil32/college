package fgc.Scene;

import fgc.Scene.GameScenes.CharacterSelect;
import fgc.Scene.GameScenes.MainMenu;
import fgc.runGame;
import tage.Engine;

import java.util.ArrayList;

public class SceneManager {
    public static GameScene currentScene;
    public ArrayList<GameScene> scenes = new ArrayList<>();

    public SceneManager() {
        scenes.add(new MainMenu());
        scenes.add(new CharacterSelect());
    }

    public void loadObjShapes() {
        for(GameScene scene: scenes) {
            scene.loadModels();
        }
    }

    public void loadTextures() {
        for(GameScene scene: scenes) {
            scene.loadTextures();
        }
    }

    public void loadObjects() {
        for(GameScene scene: scenes) {
            scene.createGameObjects();
            scene.unloadGameObjects();
        }
    }

    private void loadScene(GameScene scene) {
        if(scene != null) {
            scene.loadGameObjects();
        }
    }

    private void unloadScene(GameScene scene) {
        if(scene != null) {
            scene.unloadGameObjects();
        }
    }

    public void changeScene(GameScene scene) {
        if(currentScene != scene) {
            unloadScene(currentScene);

            currentScene = scene;

            loadScene(currentScene);
            if (runGame.debug) System.out.println("Scene Manager Loaded Scene: " + currentScene.name);
        } else {
            System.out.println("Scene is already loaded");
        }
    }
}
