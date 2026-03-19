package fgc.Scene;

import java.util.ArrayList;

public abstract class GameScene {
    public String name;
    public ArrayList<SceneElement> sceneElements = new ArrayList<>();

    public abstract void loadModels();
    public abstract void loadTextures();
    public abstract void createGameObjects();
    public abstract void loadGameObjects();
    public abstract void unloadGameObjects();
}
