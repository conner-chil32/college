package fgc.PlayerInput;

public class ActionInput {
    public Object command;
    public float frame;
    public boolean executed;

    public ActionInput(Object command, float frame, boolean executed) {
        this.command = command;
        this.frame = frame;
        this.executed = executed;
    }
}
