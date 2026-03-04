package fgc;

import fgc.Character.Characters.testCharacter;
import fgc.Character.PlayableCharacter;
import fgc.PlayerInput.InputCommands.*;
import fgc.PlayerInput.InputHandler;
import net.java.games.input.Component;
import tage.input.IInputManager;
import tage.input.InputManager;

public class Player {
    private String playerName;
    private InputManager im;
    private int playerID = 0;

    private PlayableCharacter character;

    public Player(InputManager inputManager) {
        im = inputManager;
        character = new testCharacter(this);
        if(playerID == 1) {
            character.setMirrored(true);
        }
    }

    public void initalizeControls() {
        im.associateActionWithAllKeyboards(Component.Identifier.Key.D, new cmdForward(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.A, new cmdBack(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.S, new cmdDown(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.W, new cmdUp(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.I, new cmdBlock(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        im.associateActionWithAllKeyboards(Component.Identifier.Key.O, new cmdPunch(this), IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.K, new cmdKick(this), IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.P, new cmdHeavy(this), IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.L, new cmdSpecial(this), IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
    }

    public void update(float elapsedTime) {
        character.getInputHandler().update(elapsedTime);
    }

    public void setCharacter(PlayableCharacter character) {
        this.character = character;
    }

    public PlayableCharacter getCharacter() {
        return character;
    }

    public void setPlayerName(String name) {
        playerName = name;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
