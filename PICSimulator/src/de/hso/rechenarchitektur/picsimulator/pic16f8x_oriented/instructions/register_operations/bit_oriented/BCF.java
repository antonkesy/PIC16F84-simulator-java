package de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.instructions.register_operations.bit_oriented;

import de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.instructions.Instruction;

public class BCF extends Instruction {
    public BCF(int f, int b) {
        super(0b01_00,f, b,new boolean[]{false, false, false}); //0b01_00bb_bfff_ffff
    }

    @Override
    protected void calculateOPCode() {
        opcode <<=3; //fester opcode wird erweitert um f
        opcode^=dB; // or mit f
        opcode <<=7; //opcode um d erweitert
        opcode^=fK;
    }
}
