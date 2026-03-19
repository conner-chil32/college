package a2.gameActions;

import a2.MyGame;
import net.java.games.input.Event;
import tage.input.action.AbstractInputAction;

public class CheckIfGameOver extends AbstractInputAction {
    private MyGame game;

    public CheckIfGameOver(MyGame g) {
        game = g;
    }

    @Override
    public void performAction(float time, Event evt) {
        game.checkIfGameOver();
    }
}
