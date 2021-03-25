package de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.literal_and_control_operations;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;

public class ADDLW extends Instruction {

    public ADDLW(int k) {
        super(k);
        //opcode = 11_111x_kkkk_kkkk;
        affectedStatus = new boolean[]{true, true, true};
    }
}
