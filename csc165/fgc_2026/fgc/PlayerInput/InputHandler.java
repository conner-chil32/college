package fgc.PlayerInput;

import fgc.Player;

import java.util.*;

public class InputHandler {

    private final Player player;
    private final Map<String, Boolean> inputMap = new HashMap<String, Boolean>();
    private final ArrayList<ActionInput> actionQueue = new ArrayList<ActionInput>();
    private String current = "%";


    public InputHandler(Player p) {
        player = p;
        actionQueue.add(new ActionInput(BASIC_ACTION_COMMANDS.NEUTRAL, 0f, false));
        resetInputMap();
    }

    public void printLatest() {
        if(!current.equals(actionQueue.get(actionQueue.size()-1).command.toString())){
            current = actionQueue.get(actionQueue.size()-1).command.toString();
            System.out.println(current + " : " + actionQueue.get(actionQueue.size()-1).frame);
        }
    }

    public void resetInputMap() {
        inputMap.put("UP_BACK", false);
        inputMap.put("UP", false);
        inputMap.put("UP_FORWARD", false);
        inputMap.put("BACK",false);
        inputMap.put("NEUTRAL", true);
        inputMap.put("FORWARD", false);
        inputMap.put("DOWN_BACK", false);
        inputMap.put("DOWN", false);
        inputMap.put("DOWN_FORWARD", false);
        inputMap.put("PUNCH", false);
        inputMap.put("KICK", false);
        inputMap.put("HEAVY", false);
        inputMap.put("SPECIAL", false);
        inputMap.put("BLOCK", false);
    }

    public void sendCommand(BASIC_ACTION_COMMANDS command) {
        inputMap.put("NEUTRAL", false);
        inputMap.put(command.toString(), true);
    }

    public void processInputMap(float elapsTime) {
        BASIC_ACTION_COMMANDS command = null;
        BASIC_ACTION_COMMANDS command2 = null;
        for (Map.Entry<String, Boolean> entry : inputMap.entrySet()) {
            if(entry.getValue() == true) {
                command = BASIC_ACTION_COMMANDS.valueOf(entry.getKey());
            }
        }
        // 8 Directional Input
        if(inputMap.get("DOWN") && inputMap.get("BACK")) {
            command = BASIC_ACTION_COMMANDS.DOWN_BACK;
        }
        if(inputMap.get("DOWN") && inputMap.get("FORWARD")) {
            command = BASIC_ACTION_COMMANDS.DOWN_FORWARD;
        }
        if(inputMap.get("UP") && inputMap.get("BACK")) {
            command = BASIC_ACTION_COMMANDS.UP_BACK;
        }
        if(inputMap.get("UP") && inputMap.get("FORWARD")) {
            command = BASIC_ACTION_COMMANDS.UP_FORWARD;
        }

        // Direction Attack Commands
        // Forward Attacks
        if(inputMap.get("FORWARD") && inputMap.get("PUNCH")) {
            command = (BASIC_ACTION_COMMANDS.PUNCH);
        }
        if(inputMap.get("FORWARD") && inputMap.get("KICK")) {
            command = (BASIC_ACTION_COMMANDS.KICK);
        }
        if(inputMap.get("FORWARD") && inputMap.get("HEAVY")) {
            command = (BASIC_ACTION_COMMANDS.HEAVY);
        }
        if(inputMap.get("FORWARD") && inputMap.get("SPECIAL")) {
            command = (BASIC_ACTION_COMMANDS.SPECIAL);
        }
        // Up Attacks
        if(inputMap.get("UP") && inputMap.get("PUNCH")) {
            command = (BASIC_ACTION_COMMANDS.PUNCH);
        }
        if(inputMap.get("UP") && inputMap.get("KICK")) {
            command = (BASIC_ACTION_COMMANDS.KICK);
        }
        if(inputMap.get("UP") && inputMap.get("HEAVY")) {
            command = (BASIC_ACTION_COMMANDS.HEAVY);
        }
        if(inputMap.get("UP") && inputMap.get("SPECIAL")) {
            command = (BASIC_ACTION_COMMANDS.SPECIAL);
        }
        // Down Attacks
        if(inputMap.get("DOWN") && inputMap.get("PUNCH")) {
            command = (BASIC_ACTION_COMMANDS.PUNCH);
        }
        if(inputMap.get("DOWN") && inputMap.get("KICK")) {
            command = (BASIC_ACTION_COMMANDS.KICK);
        }
        if(inputMap.get("DOWN") && inputMap.get("HEAVY")) {
            command = (BASIC_ACTION_COMMANDS.HEAVY);
        }
        if(inputMap.get("DOWN") && inputMap.get("SPECIAL")) {
            command = (BASIC_ACTION_COMMANDS.SPECIAL);
        }
        // Back Attacks
        if(inputMap.get("BACK") && inputMap.get("PUNCH")) {
            command = (BASIC_ACTION_COMMANDS.PUNCH);
        }
        if(inputMap.get("BACK") && inputMap.get("KICK")) {
            command = (BASIC_ACTION_COMMANDS.KICK);
        }
        if(inputMap.get("BACK") && inputMap.get("HEAVY")) {
            command = (BASIC_ACTION_COMMANDS.HEAVY);
        }
        if(inputMap.get("BACK") && inputMap.get("SPECIAL")) {
            command = (BASIC_ACTION_COMMANDS.SPECIAL);
        }
        // Block Command Shortcuts
        if(inputMap.get("FORWARD") && inputMap.get("BLOCK")) {
            command = BASIC_ACTION_COMMANDS.BLOCK;
        }
        if(inputMap.get("BACK") && inputMap.get("BLOCK")) {
            command = BASIC_ACTION_COMMANDS.BLOCK;
        }
        if(inputMap.get("UP") && inputMap.get("BLOCK")) {
            command = BASIC_ACTION_COMMANDS.BLOCK;
        }
        if(inputMap.get("DOWN") && inputMap.get("BLOCK")) {
            command = BASIC_ACTION_COMMANDS.DOWN_BLOCK;
        }

        if(!Objects.equals(command, actionQueue.get(actionQueue.size() - 1).command)) {
            actionQueue.add(new ActionInput(command, elapsTime, false));
        } else {
            actionQueue.set(actionQueue.size()-1, new ActionInput(actionQueue.get(actionQueue.size()-1).command, elapsTime, false));
        }
    }

    public void processActionQueue(float elapsTime) {
        for(int i=0; i < actionQueue.size()-1; i++) {
            if(actionQueue.get(i).command.equals(BASIC_ACTION_COMMANDS.PUNCH) && !actionQueue.get(i).executed) {
                if(actionQueue.get(i-1).command.equals(BASIC_ACTION_COMMANDS.FORWARD) && !actionQueue.get(i-1).executed) {
                    // Forward Punch

                    actionQueue.get(i-1).executed = true;
                    actionQueue.get(i).executed = true;
                    actionQueue.add(i+1, new ActionInput(ADVANCED_ACTION_COMMAND.FORWARD_PUNCH, elapsTime, true));

                }
            }
        }
    }

    public void printActionQueue() {
        System.out.println("_______________");
        for(ActionInput entry: actionQueue) {
            System.out.println(entry.command + ":" + entry.frame + " | " + entry.executed);
        }
        System.out.println("_______________");
    }
}

