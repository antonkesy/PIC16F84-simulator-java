package de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions;

/**
 * p56 PIC16F8X Doku
 */
public abstract class Instruction {
    private short cycles;
    protected int opcode;
    protected int fK;
    protected int dB; //d-b-k
    private boolean[] affectedStatus; //c, dz,z -> true == betroffen

    public Instruction(int opcode, int fK, boolean[] affectedStatus) {
        this.opcode = opcode;
        this.fK = fK;
        this.affectedStatus = affectedStatus;
        calculateOPCode();
    }

    public Instruction(int opcode,int fK, int dB, boolean[] affectedStatus) {
        this.opcode = opcode;
        this.fK = fK;
        this.dB = dB;
        this.affectedStatus = affectedStatus;
        calculateOPCode();
    }

    /**
     * Errechnet den OPCode/Bitmaske mit fk,db
     */
    protected abstract void calculateOPCode();

    public int getOpcode(){
        return opcode;
    }

}
