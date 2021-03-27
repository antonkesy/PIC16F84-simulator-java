package de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented;
import de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.funktionsgruppen.*;

public class PIC16F8X {
    private ProgramClock programClock;
    private ProgramClockLath programClockLath;
    private ProgramMemory programMemory;
    private RandomAccessMemory ram;
    private Stack stack;
    private StatusRegister statusRegister;
    private InstructionDecoder instructionDecoder;
    private InstructionRegister instructionRegister;
    private ArithmeticLogicUnit alu;

    public PIC16F8X() {
        programClock = new ProgramClock();
        programClockLath = new ProgramClockLath();
        programMemory = new ProgramMemory();
        ram = new RandomAccessMemory();
        stack = new Stack();
        statusRegister = new StatusRegister();
        alu = new ArithmeticLogicUnit();
        instructionDecoder = new InstructionDecoder();
        instructionRegister = new InstructionRegister();

    }

    private void testRun(){
    }

}
