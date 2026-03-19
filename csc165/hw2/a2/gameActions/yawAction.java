package a2.gameActions;

import a2.MyGame;
import net.java.games.input.Event;
import org.joml.*;
import tage.Camera;
import tage.GameObject;
import tage.input.action.AbstractInputAction;

public class yawAction extends AbstractInputAction {
    private MyGame game;
    private GameObject av;

    private String dir;
    private boolean isController;

    public yawAction(MyGame g, boolean isOnController, String direction) {
        game = g;
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
                av = game.dol;
                if (dir.equals("LEFT") || dir.equals("left") || dir.equals("Left")) {
                    av.yaw(0.02f);
                }
                if (dir.equals("RIGHT") || dir.equals("right") || dir.equals("Right")) {
                    av.yaw(-0.02f);
                }
            } else {
                float stick = evt.getValue();
                if (stick < -0.1 || stick > 0.1) {
                    av = game.dol;
                    av.yaw((float) (stick * game.elapsTime));
                }
            }
        }
    }
}
