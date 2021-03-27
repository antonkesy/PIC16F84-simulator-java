package de.hso.rechenarchitektur.picsimulator.pic16f8x;

public class Instruction {
    private InstructionType instructionType;
    private int fK;
    private int bD;

    public Instruction(InstructionType instructionType) {
        this.instructionType = instructionType;
    }

    public Instruction(InstructionType instructionType, int fk) {
        this(instructionType);
        this.fK = fk;
    }

    public Instruction(InstructionType instructionType, int fk, int bD) {
        this(instructionType, fk);
        this.bD = bD;
    }

    public InstructionType getType() {
        return instructionType;
    }

    public int getFK() {
        return fK;
    }

    public int getBD() {
        return bD;
    }
}
