package de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.funktionsgruppen;

public class ProgramClock {
    private int clock;

    public ProgramClock() {
        clock = 0;
    }

    public void setProgramClock(int value) {
        clock = value;
    }

    public int getClock() {
        return clock;
    }
}
