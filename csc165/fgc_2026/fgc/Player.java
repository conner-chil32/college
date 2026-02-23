package fgc;

import fgc.Character.PlayableCharacter;
import fgc.PlayerInput.InputBindings.*;
import fgc.PlayerInput.InputHandler;
import net.java.games.input.Component;
import org.joml.Vector3f;
import tage.HUDmanager;
import tage.input.IInputManager;
import tage.input.InputManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Player implements IPlayer{
    private final InputManager inputManager;
    private int size=0;
    private HUDmanager hudManager;
    private PlayableCharacter character;

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    private final InputHandler inputHandler;

    public Player(InputManager im, HUDmanager h) {
        inputManager = im;
        hudManager = h;
        inputHandler = new InputHandler(this);
    }

    @Override
    public void initializeControls() {
        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.W, new Command_Up(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.A, new Command_Back(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.S, new Command_Down(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.D, new Command_Forward(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.I, new Command_Block(this), IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.P, new Command_Punch(this), IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.K, new Command_Kick(this), IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.O, new Command_Heavy(this), IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        inputManager.associateActionWithAllKeyboards(Component.Identifier.Key.L, new Command_Special(this), IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);

    }

    @Override
    public void update(float elapsTime) {
        inputHandler.processInputMap(elapsTime);
        //inputHandler.processActionQueue(elapsTime);
        inputHandler.printLatest();

        inputHandler.resetInputMap();
    }
}
