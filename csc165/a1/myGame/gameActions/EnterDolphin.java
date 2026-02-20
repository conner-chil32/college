package myGame.gameActions;

import myGame.MyGame;
import net.java.games.input.Event;
import org.joml.Vector3f;
import tage.Camera;
import tage.HUDmanager;
import tage.input.action.AbstractInputAction;

public class EnterDolphin extends AbstractInputAction {
    private MyGame game;
    private Camera cam;
    private HUDmanager hud;

    public EnterDolphin(MyGame g, HUDmanager h) {
        game = g;
        cam = game.cam;
        hud = h;
    }
    @Override
    public void performAction(float time, Event evt) {
        if(!game.paused) {
            game.isOnDolphin = !game.isOnDolphin;
            if (!game.isOnDolphin) {
                cam.setLocation(game.dol.getWorldLocation().add(game.dol.getWorldRightVector().mul(-1.3f)));
                if(game.getDistanceFromDolphinToHome() < 3f) {
                    Vector3f hud1Color = new Vector3f(0,1,0);
                    hud.setHUD2("You need more pictures", hud1Color, 15, 50);
                }
                if(game.getDistanceFromDolphinToHome() < 3f && game.score == 3) {
                    game.img4.setTextureImage(game.imageTextures.get(0));
                    game.img5.setTextureImage(game.imageTextures.get(1));
                    game.img6.setTextureImage(game.imageTextures.get(2));
                    Vector3f hud1Color = new Vector3f(0,1,0);
                    game.paused = true;
                    hud.setHUD2("YOU WIN!!", hud1Color, 15, 50);
                }
            }
        }
    }
}
