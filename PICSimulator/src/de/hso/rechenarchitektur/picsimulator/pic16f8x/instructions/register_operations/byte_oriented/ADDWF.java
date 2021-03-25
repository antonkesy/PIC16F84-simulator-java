package de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.register_operations.byte_oriented;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;

public class ADDWF extends Instruction {
    public ADDWF(int f, int d) {
        super(f, d);
        //opcode = 00_0111_d_fff_ffff;
        affectedStatus = new boolean[]{true, true, true};
    }
}
