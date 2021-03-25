package de.hso.rechenarchitektur.picsimulator.pic16f8x;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.funktionsgruppen.*;

public class PIC16F8X {
    private ProgramClock programClock;
    private ProgramClockLath programClockLath;
    private ProgramMemory programMemory;
    private RandomAccessMemory randomAccessMemory;
    private Stack stack;
    private StatusRegister statusRegister;

    public PIC16F8X() {
        programClock = new ProgramClock();
        programClockLath = new ProgramClockLath();
        programMemory = new ProgramMemory();
        randomAccessMemory = new RandomAccessMemory();
        stack = new Stack();
        statusRegister = new StatusRegister();
    }

}
