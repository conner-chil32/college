package fgc.PlayerInput.InputBindings;

import fgc.Player;
import fgc.PlayerInput.BASIC_ACTION_COMMANDS;
import net.java.games.input.Event;
import tage.input.action.AbstractInputAction;

public class Command_Back extends AbstractInputAction {
    private Player player;

    public Command_Back(Player p) {
        player = p;
    }
    @Override
    public void performAction(float time, Event evt) {
        player.getInputHandler().sendCommand(BASIC_ACTION_COMMANDS.BACK);
    }
}
