package fgc.PlayerInput;

import java.util.HashMap;
import java.util.Map;

public class InputMap {
    private final Map<String, Boolean> inputHashMap = new HashMap<String, Boolean>();

    public InputMap() {
        inputHashMap.put("UP_BACK", false);
        inputHashMap.put("UP", false);
        inputHashMap.put("UP_FORWARD", false);
        inputHashMap.put("BACK",false);
        inputHashMap.put("NEUTRAL", true);
        inputHashMap.put("FORWARD", false);
        inputHashMap.put("DOWN_BACK", false);
        inputHashMap.put("DOWN", false);
        inputHashMap.put("DOWN_FORWARD", false);
        inputHashMap.put("PUNCH", false);
        inputHashMap.put("KICK", false);
        inputHashMap.put("HEAVY", false);
        inputHashMap.put("SPECIAL", false);
        inputHashMap.put("BLOCK", false);
    }

    public void reset() {
        for(Map.Entry<String, Boolean> entry: inputHashMap.entrySet()) {
            if(entry.getKey().equals("NEUTRAL")) {
                entry.setValue(true);
            } else {
                entry.setValue(false);
            }
        }
    }

    public void sendCommand(BASIC_INPUT command) {
        inputHashMap.put("NEUTRAL", false);
        inputHashMap.put(command.toString(), true);
    }

    public void processCommand(float elapsedTime, InputQueue queue) {
        BASIC_INPUT command = null;
        for(Map.Entry<String, Boolean> entry: inputHashMap.entrySet()) {
            if(entry.getValue().equals(true)) {
                command = BASIC_INPUT.valueOf(entry.getKey());
            }
        }

        // 8 Directional Input
        if(inputHashMap.get("DOWN") && inputHashMap.get("BACK")) {
            command = BASIC_INPUT.DOWN_BACK;
        }
        if(inputHashMap.get("DOWN") && inputHashMap.get("FORWARD")) {
            command = BASIC_INPUT.DOWN_FORWARD;
        }
        if(inputHashMap.get("UP") && inputHashMap.get("BACK")) {
            command = BASIC_INPUT.UP_BACK;
        }
        if(inputHashMap.get("UP") && inputHashMap.get("FORWARD")) {
            command = BASIC_INPUT.UP_FORWARD;
        }

        // Direction Attack Commands
        // Forward Attacks
        if(inputHashMap.get("FORWARD") && inputHashMap.get("PUNCH")) {
            command = (BASIC_INPUT.PUNCH);
        }
        if(inputHashMap.get("FORWARD") && inputHashMap.get("KICK")) {
            command = (BASIC_INPUT.KICK);
        }
        if(inputHashMap.get("FORWARD") && inputHashMap.get("HEAVY")) {
            command = (BASIC_INPUT.HEAVY);
        }
        if(inputHashMap.get("FORWARD") && inputHashMap.get("SPECIAL")) {
            command = (BASIC_INPUT.SPECIAL);
        }
        // Up Attacks
        if(inputHashMap.get("UP") && inputHashMap.get("PUNCH")) {
            command = (BASIC_INPUT.PUNCH);
        }
        if(inputHashMap.get("UP") && inputHashMap.get("KICK")) {
            command = (BASIC_INPUT.KICK);
        }
        if(inputHashMap.get("UP") && inputHashMap.get("HEAVY")) {
            command = (BASIC_INPUT.HEAVY);
        }
        if(inputHashMap.get("UP") && inputHashMap.get("SPECIAL")) {
            command = (BASIC_INPUT.SPECIAL);
        }
        // Down Attacks
        if(inputHashMap.get("DOWN") && inputHashMap.get("PUNCH")) {
            command = (BASIC_INPUT.PUNCH);
        }
        if(inputHashMap.get("DOWN") && inputHashMap.get("KICK")) {
            command = (BASIC_INPUT.KICK);
        }
        if(inputHashMap.get("DOWN") && inputHashMap.get("HEAVY")) {
            command = (BASIC_INPUT.HEAVY);
        }
        if(inputHashMap.get("DOWN") && inputHashMap.get("SPECIAL")) {
            command = (BASIC_INPUT.SPECIAL);
        }
        // Back Attacks
        if(inputHashMap.get("BACK") && inputHashMap.get("PUNCH")) {
            command = (BASIC_INPUT.PUNCH);
        }
        if(inputHashMap.get("BACK") && inputHashMap.get("KICK")) {
            command = (BASIC_INPUT.KICK);
        }
        if(inputHashMap.get("BACK") && inputHashMap.get("HEAVY")) {
            command = (BASIC_INPUT.HEAVY);
        }
        if(inputHashMap.get("BACK") && inputHashMap.get("SPECIAL")) {
            command = (BASIC_INPUT.SPECIAL);
        }
        // Block Command Shortcuts
        if(inputHashMap.get("FORWARD") && inputHashMap.get("BLOCK")) {
            command = BASIC_INPUT.BLOCK;
        }
        if(inputHashMap.get("BACK") && inputHashMap.get("BLOCK")) {
            command = BASIC_INPUT.BLOCK;
        }
        if(inputHashMap.get("UP") && inputHashMap.get("BLOCK")) {
            command = BASIC_INPUT.BLOCK;
        }
        if(inputHashMap.get("DOWN") && inputHashMap.get("BLOCK")) {
            command = BASIC_INPUT.DOWN_BLOCK;
        }

        // null movement
        if(inputHashMap.get("DOWN") && inputHashMap.get("UP")) {
            command = BASIC_INPUT.NEUTRAL;
        }
        if(inputHashMap.get("FORWARD") && inputHashMap.get("BACK")) {
            command = BASIC_INPUT.NEUTRAL;
        }

        queue.add(new InputAction(command, elapsedTime));
    }

    public void printHashMap() {
        for(Map.Entry<String, Boolean> entry: inputHashMap.entrySet()) {
            if(entry.getValue().equals(true)) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

}
