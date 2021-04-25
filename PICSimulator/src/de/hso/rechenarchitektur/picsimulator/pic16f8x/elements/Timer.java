package de.hso.rechenarchitektur.picsimulator.pic16f8x.elements;

public class Timer {
    private boolean wasLastRA4_T0CKIFlankUp;
    private float timer = 0;

    public void addTimer(float value) {
        this.timer += value;
    }

    public void setTimer(float value) {
        this.timer = value;
    }

    public float getTimer() {
        return timer;
    }

    public boolean wasLastRA4_T0CKIFlankUp() {
        return wasLastRA4_T0CKIFlankUp;
    }

    public void setWasLastRA4_T0CKIFlankUp(boolean wasLastRA4_T0CKIFlankUp) {
        this.wasLastRA4_T0CKIFlankUp = wasLastRA4_T0CKIFlankUp;
    }
}
