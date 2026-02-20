package myGame.gameActions;

import myGame.MyGame;
import net.java.games.input.Event;
import org.joml.Vector3f;
import org.w3c.dom.Text;
import tage.GameObject;
import tage.HUDmanager;
import tage.RenderSystem;
import tage.TextureImage;
import tage.input.action.AbstractInputAction;

public class TakePicture extends AbstractInputAction {
    private MyGame game;
    private int picturesTaken = 0;
    private HUDmanager hud;

    public TakePicture(MyGame g, HUDmanager h) {
        game = g;
        hud = h;
    }
    @Override
    public void performAction(float time, Event evt) {
        if (!game.paused) {
            if (game.closestPlanet == null) {
                System.out.println("MOVE CLOSER!");
                Vector3f hud2Color = new Vector3f(0, 1, 0);
                hud.setHUD2("Move Closer", hud2Color, 15, 50);
            } else {
                TextureImage picture = game.closestPlanet.getTextureImage();
                Vector3f hud2Color = new Vector3f(0, 1, 0);
                if (game.imageTextures.contains(picture)) {
                    System.out.println("ALREADY CONTAINS THAT IMAGE");
                    hud.setHUD2("You already have taken a picture of this", hud2Color, 15, 50);
                } else {
                    System.out.println("DOESNT CONTAIN THAT IMAGE");
                    hud.setHUD2("Picture Taken!", hud2Color, 15, 50);
                    game.images.get(picturesTaken).setTextureImage(picture);
                    game.imageTextures.set(picturesTaken, picture);
                    picturesTaken++;
                    game.score++;
                }
            }
        }
    }
}
