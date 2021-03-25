package de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.literal_and_control_operations;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;

public class ADDLW extends Instruction {

    public ADDLW(int k) {
        super(0b11_111, k,new boolean[]{true, true, true});
    }

    @Override
    protected void calculateOPCode() {
        opcode <<=1; //fester opcode wird erweitert um x -> x ist egal
        opcode <<=8; //opcode um d erweitert
        opcode^=fK;
    }
}
