package game.Scenes;

import org.joml.Matrix4f;
import tage.GameObject;
import tage.TextureImage;
import tage.input.InputManager;
import tage.shapes.*;

public class MainMenu extends GameScene{

    public MainMenu() {
        sceneName = "Main Menu";
        elements.add(new SceneElement(GameObject.root(), new Plane(), new TextureImage("brick1.jpg"), new Matrix4f().translation(0,0,0), new Matrix4f().scale(2.0f)));
    }

    @Override
    public void loadSceneElements() {
        for(SceneElement element: elements) {
            element.object = new GameObject(element.parent, element.shape, element.texture);
            element.setTranslation(element.translation);
            element.setScale(element.scale);
        }

        elements.get(0).object.setLocalRotation(new Matrix4f().rotateX(1.55f));
    }

    @Override
    public void loadSceneControls(InputManager im) {

    }
}
