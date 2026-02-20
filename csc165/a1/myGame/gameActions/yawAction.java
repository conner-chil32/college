package myGame.gameActions;

import myGame.MyGame;
import net.java.games.input.Event;
import org.joml.*;
import tage.Camera;
import tage.GameObject;
import tage.input.action.AbstractInputAction;

public class yawAction extends AbstractInputAction {
    private MyGame game;
    private GameObject av;
    private Camera cam;

    private String dir;
    private boolean isController;

    public yawAction(MyGame g, boolean isOnController, String direction) {
        game = g;
        cam = g.cam;
        dir = direction;
        isController = isOnController;
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
                    if (dir.equals("LEFT") || dir.equals("left") || dir.equals("Left")) {
                        av.yaw(0.02f);
                    }
                    if (dir.equals("RIGHT") || dir.equals("right") || dir.equals("Right")) {
                        av.yaw(-0.02f);
                    }
                    game.img1.setLocalLocation(av.getLocalUpVector().mul(2.0f).add(av.getLocalRightVector().mul(1.4f)));
                    game.img2.setLocalLocation(av.getLocalUpVector().mul(1.5f).add(av.getLocalRightVector().mul(1.4f)));
                    game.img3.setLocalLocation(av.getLocalUpVector().mul(1.0f).add(av.getLocalRightVector().mul(1.4f)));
                } else {
                    if (dir.equals("LEFT") || dir.equals("left") || dir.equals("Left")) {
                        cam.yaw(0.02f);
                    }
                    if (dir.equals("RIGHT") || dir.equals("right") || dir.equals("Right")) {
                        cam.yaw(-0.02f);
                    }
                }
            } else {
                float stick = evt.getValue();
                if (stick < -0.1 || stick > 0.1) {
                    if (game.isOnDolphin) {
                        av = game.dol;
                        av.yaw((float) (stick * game.elapsTime));
                        game.img1.setLocalLocation(av.getLocalUpVector().mul(2.0f).add(av.getLocalRightVector().mul(1.4f)));
                        game.img2.setLocalLocation(av.getLocalUpVector().mul(1.5f).add(av.getLocalRightVector().mul(1.4f)));
                        game.img3.setLocalLocation(av.getLocalUpVector().mul(1.0f).add(av.getLocalRightVector().mul(1.4f)));
                    }
                }
            }
        }
    }
}
