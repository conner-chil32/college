package fgc.PlayerInput;

import fgc.Player;

public class InputHandler {

    private final Player player;
    private final InputMap inputMap;
    private final InputQueue inputQueue;

    public InputHandler(Player p) {
        player = p;
        inputMap = new InputMap();
        inputQueue = new InputQueue();
    }

    public void update(float elapsedTime) {
        inputMap.processCommand(elapsedTime, inputQueue);

        String temp = inputQueue.printLatest();
        if(!temp.contains("%")) {
            System.out.println(temp);
        }

        inputMap.reset();   // called at the end of every frame;
    }

    public Player getPlayer() {
        return player;
    }

    public InputMap getInputMap() {
        return inputMap;
    }

    public InputQueue getInputQueue() {
        return inputQueue;
    }
}
