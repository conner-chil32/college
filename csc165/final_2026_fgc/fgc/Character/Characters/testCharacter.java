package fgc.Character.Characters;

import fgc.Character.PlayableCharacter;
import fgc.Player;

public class testCharacter extends PlayableCharacter {
    public testCharacter(Player p) {
        super(p);
        name = "Test";
        health = 2000.0f;
    }

    @Override
    public void defineMoveset() {

    }
}
