package fgc.Scene;

import tage.GameObject;
import tage.ObjShape;
import tage.TextureImage;

public class SceneElement {
    public GameObject object;
    public GameObject parent;
    public ObjShape model;
    public TextureImage texture;

    public SceneElement(){}

    public TextureImage getTexture() {
        return texture;
    }

    public void setTexture(TextureImage texture) {
        this.texture = texture;
    }

    public ObjShape getModel() {
        return model;
    }

    public void setModel(ObjShape model) {
        this.model = model;
    }
}
