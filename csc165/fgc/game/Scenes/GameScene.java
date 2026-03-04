package game.Scenes;

import tage.GameObject;
import tage.ObjShape;
import tage.input.InputManager;

import java.util.ArrayList;

public abstract class GameScene {
    public String sceneName;
    public ArrayList<SceneElement> elements = new ArrayList<>();

    public abstract void loadSceneElements();
    public abstract void loadSceneControls(InputManager im);
}
