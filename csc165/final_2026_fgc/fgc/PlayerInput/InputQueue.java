package fgc.PlayerInput;

import java.util.ArrayList;

public class InputQueue {

    private final ArrayList<InputAction> actions = new ArrayList<InputAction>();
    private String currentInput = "";

    public InputQueue(){
        actions.add(new InputAction(BASIC_INPUT.NEUTRAL, 0f));
    }

    public void add(InputAction inputAction) {
        if(actions.get(adjustedSize()).command != inputAction.command) {
            actions.add(inputAction);
        } else {
            set(adjustedSize(), new InputAction(getCommand(adjustedSize()),inputAction.time));
        }
    }

    public InputAction get(int index) {
        return actions.get(index);
    }

    public BASIC_INPUT getCommand(int index) {
        return actions.get(index).command;
    }

    public InputLock getLock(int index) {
        return actions.get(index).inputLock;
    }

    public void set(int index, InputAction inputAction) {
        actions.set(index, inputAction);
    }

    public int size() {
        return actions.size();
    }

    public int adjustedSize() {
        return actions.size()-1;
    }

    public String printLatest() {
        String temp = currentInput;
        if(!currentInput.equals(actions.get(adjustedSize()).command.toString())) {
            currentInput = actions.get(adjustedSize()).command.toString();

            temp = "Command: " + actions.get(adjustedSize()).command.toString();
            temp += " | Time: " + actions.get(adjustedSize()).time;
            temp += " | Lock Status: " + actions.get(adjustedSize()).inputLock.lockState;
            if(actions.get(adjustedSize()).inputLock.lockState) {
                temp += " | Lock Duration: " + actions.get(adjustedSize()).inputLock.duration;
            }
        } else {
            temp = "%";
        }
        return temp;
    }
}
