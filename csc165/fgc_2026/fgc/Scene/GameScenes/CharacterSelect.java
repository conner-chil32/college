package fgc.Scene.GameScenes;

import com.jogamp.graph.ui.Scene;
import fgc.Scene.GameScene;
import fgc.Scene.SceneElement;
import fgc.runGame;
import org.joml.Matrix4f;
import tage.Engine;
import tage.GameObject;
import tage.TextureImage;
import tage.shapes.Sphere;

public class CharacterSelect extends GameScene {
    private Engine engine;

    public CharacterSelect() {
        name = "Character Select";
        sceneElements.add(new SceneElement());
    }
    @Override
    public void loadModels() {
        sceneElements.get(0).setModel(new Sphere());
        if(runGame.debug) System.out.println("Loaded Models from Character Select Scene");
    }

    @Override
    public void loadTextures() {
        sceneElements.get(0).setTexture(new TextureImage("ice.jpg"));
        if(runGame.debug) System.out.println("Loaded Textures from Character Select Scene");
    }

    @Override
    public void createGameObjects() {
        sceneElements.get(0).object = new GameObject(
                GameObject.root(),
                sceneElements.get(0).model,
                sceneElements.get(0).texture
        );
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
