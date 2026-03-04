package game.Scenes;

import org.joml.Matrix4f;
import tage.GameObject;
import tage.ObjShape;
import tage.TextureImage;
import tage.rml.Matrix4;

public class SceneElement {
    public GameObject parent, object;
    public ObjShape shape;
    public TextureImage texture;
    public Matrix4f translation, scale;

    public SceneElement(GameObject parent, ObjShape shape, TextureImage texture, Matrix4f translation, Matrix4f scale) {
        if(parent == null) parent = GameObject.root();
        else this.parent = parent;

        this.shape = shape;
        this.texture = texture;
        this.translation = translation;
        this.scale = scale;
    }

    public void setTranslation(Matrix4f translation) {
        object.setLocalTranslation(translation);
    }

    public void setScale(Matrix4f scale) {
        object.setLocalScale(scale);
    }
}
