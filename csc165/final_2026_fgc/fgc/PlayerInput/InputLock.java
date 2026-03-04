package fgc.PlayerInput;

public class InputLock {
    public boolean lockState = false;
    public float duration;

    public InputLock(boolean lockState, float duration) {
        this.lockState = lockState;
        this.duration = duration;
    }

    public void setLock(float duration) {
        lockState = true;
        this.duration = duration;
    }

    public void unlock() {
        lockState = false;
    }
}
