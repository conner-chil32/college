package a2.gameActions;

import a2.MyGame;
import net.java.games.input.Event;
import org.joml.Vector3f;
import org.joml.Vector4f;
import tage.Camera;
import tage.GameObject;
import tage.input.action.AbstractInputAction;

public class BckAction extends AbstractInputAction {
    private final MyGame game;
    private GameObject av;
    private Vector3f oldPosition, newPosition;
    private Vector4f fwdDirection;
    private final boolean isController;

    public BckAction(MyGame g, boolean isOnController) {
        game = g;
        isController = isOnController;
    }

    @Override
    public void performAction(float time, Event evt) {
        if (!game.paused) {
            if (!isController) {
                av = game.dol;
                oldPosition = av.getWorldLocation();
                fwdDirection = new Vector4f(0f, 0f, 1f, 1f);
                fwdDirection.mul(av.getWorldRotation());
                fwdDirection.mul(0.02f);
                newPosition = oldPosition.sub(fwdDirection.x(),
                        fwdDirection.y(), fwdDirection.z());
                av.setLocalLocation(newPosition);
                }
            } else {
                float stick = evt.getValue();
                if (stick < -0.1 || stick > 0.1) {
                    av = game.dol;
                    oldPosition = av.getWorldLocation();
                    fwdDirection = new Vector4f(0f, 0f, 1f, 1f);
                    fwdDirection.mul(av.getWorldRotation());
                    fwdDirection.mul((float) (stick * game.elapsTime));
                    newPosition = oldPosition.sub(fwdDirection.x(),
                            fwdDirection.y(), fwdDirection.z());
                    av.setLocalLocation(newPosition);
                }
            }
        }
    }
