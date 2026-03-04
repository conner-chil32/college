package fgc.PlayerInput;

public class InputAction {
    public BASIC_INPUT command;
    public float time;
    public InputLock inputLock = new InputLock(false, 0f);

    public InputAction(BASIC_INPUT command, float time) {
        this.command = command;
        this.time = time;
    }

    public void setInputLock(float duration) {
        inputLock.setLock(duration);
    }

    public void updateLock(float elapsedTime) {
        if(elapsedTime >= time + inputLock.duration) {
            inputLock.unlock();
        }
    }


}
