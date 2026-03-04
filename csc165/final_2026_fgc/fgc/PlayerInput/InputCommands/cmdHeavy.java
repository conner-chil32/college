package fgc.PlayerInput.InputCommands;

import fgc.Player;
import fgc.PlayerInput.BASIC_INPUT;
import net.java.games.input.Event;
import tage.input.action.AbstractInputAction;

public class cmdHeavy extends AbstractInputAction {
    private Player player;

    public cmdHeavy(Player player) {
        this.player = player;
    }

    @Override
    public void performAction(float time, Event evt) {
        player.getCharacter().getInputHandler().getInputMap().sendCommand(BASIC_INPUT.HEAVY);
    }
}
