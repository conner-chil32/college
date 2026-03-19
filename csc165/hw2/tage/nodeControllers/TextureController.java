package tage.nodeControllers;

import tage.Engine;
import tage.GameObject;
import tage.NodeController;
import tage.TextureImage;

/**
 * This is the texture node controller, all it handles the changing if the textures of all node's
 * targeted to a given texture, this can be used to change the texture of a set of game objects or
 * nodes en-mass.
 */

public class TextureController extends NodeController {
    private TextureImage texture;
    private Engine engine;

    public TextureController(Engine e, TextureImage tex) {
        super();
        texture = tex;
        engine = e;
    }


    @Override
    public void apply(GameObject t) {
        t.setTextureImage(texture);
    }
}
