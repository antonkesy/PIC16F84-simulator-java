package de.hso.rechenarchitektur.picsimulator.pic16f8x;


public class InstructionDecoder {

    public static Instruction decodeInstruction(String line) {
        return new Instruction(InstructionType.NOP);
    }
}
