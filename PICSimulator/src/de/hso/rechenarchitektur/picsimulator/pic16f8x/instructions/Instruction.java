package de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions;

public abstract class Instruction {
    protected short cycles;
    protected int opcode;
    protected int fK;
    protected int dB; //d-b-k
    protected boolean[] affectedStatus; //c, dz,z -> true == betroffen

    public Instruction(int fK) {
        this.fK = fK;
    }

    public Instruction(int fK, int dB) {
        this.fK = fK;
        this.dB = dB;
    }

}
