package de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.register_operations.bit_oriented;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;

public class BCF extends Instruction {
    public BCF(int f, int b) {
        super(f, b);
        //opcode = 01_00bb_bfff_ffff;
        affectedStatus = new boolean[]{false, false, false};
    }
}
