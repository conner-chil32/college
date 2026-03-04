package fgc.Character;

import fgc.Player;
import fgc.PlayerInput.InputHandler;

import java.util.ArrayList;

public abstract class PlayableCharacter {

    private InputHandler inputHandler;
    private Player player;
    private boolean isMirrored = false;

    public String name;
    public float health;


    public boolean isBlocking;
    public float frameAdvantage = 0f;


    public PlayableCharacter(Player p) {
        player = p;
        inputHandler = new InputHandler(player);
        defineMoveset();
    }

    public void update(float elapsedTime) {
        inputHandler.update(elapsedTime);
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void setMirrored(boolean mirrored) {
        isMirrored = mirrored;
    }

    public boolean isMirrored() {
        return isMirrored;
    }

    public abstract void defineMoveset();
}
