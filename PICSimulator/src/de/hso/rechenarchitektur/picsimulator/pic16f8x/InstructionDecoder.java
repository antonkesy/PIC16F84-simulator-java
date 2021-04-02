package de.hso.rechenarchitektur.picsimulator.pic16f8x;

/**
 * Wandelt den Opcode in Instruktions und Werte um
 */
public class InstructionDecoder {

    /**
     * Gibt die gefuellte Instruktion dem opcode entsprechend zurueck
     *
     * @param opcode
     * @return null wenn ungueltiger opcode
     */
    public static Instruction decodeInstruction(int opcode) {
        //Fist two binary digits of opcode
        int opcodeMSbs = opcode >>> 12;
        Instruction result;
        //Decides what kind of instruction category opcode is
        switch (opcodeMSbs) {
            case 0b0:
                result = byteOrientedInstruction(opcode);
                break;
            case 0b01:
                result = bitOrientedInstruction(opcode);
                break;
            case 0b11:
                result = literalOrientedInstruction(opcode);
                break;
            case 0b10:
                result = controlOrientedInstruction(opcode);
                break;
            default:
                //Illegal opcode
                result = null;
        }
        return result;
    }

    /**
     * Gibt BYTE-ORIENTED FILE REGISTER Instruktion dem opcode entsprechend zurueck
     *
     * @param opcode
     * @return
     */
    private static Instruction byteOrientedInstruction(int opcode) {
        //3rd Byte from right to decide which instruction opcode is
        int instructionCode = opcode & 0b00_1111_0000_0000;
        instructionCode >>>= 8;
        Instruction resultInstruction = null;
        switch (instructionCode) {
            case 0b111:
                //ADDWF
                System.out.println("ADDWF");
                resultInstruction = new Instruction(InstructionType.ADDWF, opcode & 0b00_0000_0111_1111, is8BitOne(opcode) ? 1 : 0);
                break;
            case 0b101:
                //ANDWF
                break;
            case 0b1:
                //CLRF & CLRW
                break;
            case 0b1001:
                //COMF
                break;
            case 0b11:
                //DECF
                break;
            case 0b1011:
                //DECFSZ
                break;
            case 0b1010:
                //INCF
                break;
            case 0b1111:
                //INCFSZ
                break;
            case 0b0100:
                //IORWF
                break;
            case 0b1000:
                //MOVF
                break;
            case 0b0000:
                //MOVWF & NOP
                int opcodeLSBs = opcode & 0b00_0000_1001_1111;
                if (opcodeLSBs == 0) {
                    //NOP
                } else {
                    //MOVWF
                }
                break;
            case 0b1101:
                //RLF
                break;
            case 0b1100:
                //RRF
                break;
            case 0b0010:
                //SUBWF
                break;
            case 0b1110:
                //SWAPF
                break;
            case 0b0110:
                //XORWF
                break;
        }
        return resultInstruction;
    }

    /**
     * Gibt BIT-ORIENTED FILE REGISTER Instruktion dem opcode entsprechend zurueck
     *
     * @param opcode
     * @return
     */
    private static Instruction bitOrientedInstruction(int opcode) {
        return new Instruction(InstructionType.NOP);
    }

    /**
     * Gibt LITERAL-ORIENTED FILE REGISTER Instruktion dem opcode entsprechend zurueck
     *
     * @param opcode
     * @return
     */
    private static Instruction literalOrientedInstruction(int opcode) {
        return new Instruction(InstructionType.NOP);
    }

    public static boolean is8BitOne(int code) {
        code &= 0b1000_0000;
        code >>>= 7;
        return code == 1;
    }

    /**
     * Gibt CONTROL-ORIENTED FILE REGISTER Instruktion dem opcode entsprechend zurueck
     *
     * @param opcode
     * @return
     */
    private static Instruction controlOrientedInstruction(int opcode) {
        return new Instruction(InstructionType.NOP);
    }
}
