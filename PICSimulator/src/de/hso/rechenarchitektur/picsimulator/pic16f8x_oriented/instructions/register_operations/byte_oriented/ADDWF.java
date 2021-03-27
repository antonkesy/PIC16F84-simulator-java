package de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.instructions.register_operations.byte_oriented;

import de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.instructions.Instruction;

public class ADDWF extends Instruction {
    public ADDWF(int f, int d) {
        super(0b00_0111_1111_1111, f, d, new boolean[]{true, true, true});
        calculateOPCode();
    }

    @Override
    protected void calculateOPCode() {
        //TODO("set f und d in opcode")
    }
}
