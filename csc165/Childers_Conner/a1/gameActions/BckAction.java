package a1.gameActions;

import a1.MyGame;
import net.java.games.input.Event;
import org.joml.Vector3f;
import org.joml.Vector4f;
import tage.Camera;
import tage.GameObject;
import tage.input.action.AbstractInputAction;

public class BckAction extends AbstractInputAction {
    private MyGame game;
    private GameObject av;
    private Vector3f oldPosition, newPosition;
    private Vector4f fwdDirection;
    private Camera cam;
    private Vector3f camFwdDirection;
    private boolean isController;

    public BckAction(MyGame g, boolean isOnController) {
        game = g;
        cam = game.cam;
        isController = isOnController;
    }

    @Override
    public void performAction(float time, Event evt) {
        if (!game.paused) {
            if (!isController) {
                if (game.isOnDolphin) {
                    av = game.dol;
                    oldPosition = av.getWorldLocation();
                    fwdDirection = new Vector4f(0f, 0f, 1f, 1f);
                    fwdDirection.mul(av.getWorldRotation());
                    fwdDirection.mul(0.02f);
                    newPosition = oldPosition.sub(fwdDirection.x(),
                            fwdDirection.y(), fwdDirection.z());
                    av.setLocalLocation(newPosition);
                } else {
                    oldPosition = cam.getLocation();
                    camFwdDirection = cam.getN();
                    camFwdDirection.mul(0.02f);
                    newPosition = oldPosition.sub(camFwdDirection.x(), camFwdDirection.y(), camFwdDirection.z());
                    cam.setLocation(newPosition);
                }
            } else {
                float stick = evt.getValue();
                if (stick < -0.1 || stick > 0.1) {
                    if (game.isOnDolphin) {
                        av = game.dol;
                        oldPosition = av.getWorldLocation();
                        fwdDirection = new Vector4f(0f, 0f, 1f, 1f);
                        fwdDirection.mul(av.getWorldRotation());
                        fwdDirection.mul((float) (stick * game.elapsTime));
                        newPosition = oldPosition.sub(fwdDirection.x(),
                                fwdDirection.y(), fwdDirection.z());
                        av.setLocalLocation(newPosition);
                    } else {
                        oldPosition = cam.getLocation();
                        camFwdDirection = cam.getN();
                        camFwdDirection.mul((float) (stick * game.elapsTime));
                        newPosition = oldPosition.sub(camFwdDirection.x(), camFwdDirection.y(), camFwdDirection.z());
                        cam.setLocation(newPosition);
                    }
                }
            }
        }
    }
}
