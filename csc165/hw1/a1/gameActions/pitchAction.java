package a1.gameActions;

import a1.MyGame;
import net.java.games.input.Event;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import tage.Camera;
import tage.GameObject;
import tage.input.action.AbstractInputAction;

import java.util.Optional;

public class pitchAction extends AbstractInputAction {
    private MyGame game;
    private GameObject av;
    private Camera cam;
    private Matrix4f oldRotation, newRotation, rotAroundAvatarUp;
    private Vector4f oldUp;

    private Vector3f rightVector, upVector, fwdVector;
    private String dir;
    private boolean isController;

    public pitchAction(MyGame g, boolean isOnController, String direction) {
        game = g;
        cam = game.cam;
        isController = isOnController;
        dir = direction;
    }

    @Override
    public void performAction(float time, Event evt) {
        if (!game.paused) {
            if (!isController) {
                if (dir.isBlank()) {
                    throw new NullPointerException("Direction must be specified if using on keyboard");
                }

                if (game.isOnDolphin) {
                    av = game.dol;
                    if (dir.equals("UP") || dir.equals("up") || dir.equals("Up")) {
                        av.pitch(0.02f);

                    }
                    if (dir.equals("DOWN") || dir.equals("down") || dir.equals("Down")) {
                        av.pitch(-0.02f);
                    }
                    game.img1.setLocalLocation(av.getLocalUpVector().mul(2.0f).add(av.getLocalRightVector().mul(1.4f)));
                    game.img2.setLocalLocation(av.getLocalUpVector().mul(1.5f).add(av.getLocalRightVector().mul(1.4f)));
                    game.img3.setLocalLocation(av.getLocalUpVector().mul(1.0f).add(av.getLocalRightVector().mul(1.4f)));
                } else {
                    if (dir.equals("UP") || dir.equals("up") || dir.equals("Up")) {
                        cam.pitch(0.02f);
                    }
                    if (dir.equals("DOWN") || dir.equals("down") || dir.equals("Down")) {
                        cam.pitch(-0.02f);
                    }
                }
            } else {
                float stick = evt.getValue();
                if (stick < -0.1 || stick > 0.1) {
                    if (game.isOnDolphin) {
                        av = game.dol;
                        av.pitch((float) (stick * game.elapsTime));
                        game.img1.setLocalLocation(av.getLocalUpVector().mul(2.0f));
                        game.img2.setLocalLocation(av.getLocalUpVector().mul(1.5f));
                        game.img3.setLocalLocation(av.getLocalUpVector().mul(1.0f));
                    }
                }
            }
        }
    }
}
