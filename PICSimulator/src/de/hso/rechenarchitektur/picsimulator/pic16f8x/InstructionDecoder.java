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
                resultInstruction = new Instruction(InstructionType.ADDWF, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b101:
                //ANDWF
                resultInstruction = new Instruction(InstructionType.ANDWF, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b1:
                //CLRF & CLRW
                //TODO split and load only necessary
                resultInstruction = new Instruction(is7thBitOne(opcode) ? InstructionType.CLRF : InstructionType.CLRW, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b1001:
                //COMF
                resultInstruction = new Instruction(InstructionType.COMF, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b11:
                //DECF
                resultInstruction = new Instruction(InstructionType.DECF, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b1011:
                //DECFSZ
                resultInstruction = new Instruction(InstructionType.DECFSZ, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b1010:
                //INCF
                resultInstruction = new Instruction(InstructionType.INCF, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b1111:
                //INCFSZ
                resultInstruction = new Instruction(InstructionType.INCFSZ, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b0100:
                //IORWF
                resultInstruction = new Instruction(InstructionType.IORWF, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b1000:
                //MOVF
                resultInstruction = new Instruction(InstructionType.MOVF, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
                break;
            case 0b0000:
                //MOVWF & NOP
                int opcodeLSBs = opcode & 0b00_0000_1001_1111;
                if (opcodeLSBs == 0) {
                    //NOP
                    resultInstruction = new Instruction(InstructionType.NOP);
                } else {
                    //MOVWF
                    resultInstruction = new Instruction(InstructionType.MOVWF, opcode & 0b00_0000_0111_1111, get7thBit(opcode));
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

    /**
     * Gibt zurueck true, wenn das 7. Bit (von rechts) == 1
     *
     * @param code
     * @return
     */
    private static boolean is7thBitOne(int code) {
        return getNBit(code, 7) == 1;
    }

    /**
     * Gibt das 7. Bit (von rechts) zurueck
     *
     * @param code
     * @return
     */
    private static int get7thBit(int code) {
        return getNBit(code, 7);
    }

    /**
     * Gibt Bit an Position N zurueck
     * LSb == 0
     *
     * @param code
     * @param n
     * @return
     */
    private static int getNBit(int code, int n) {
        int mask = 1;
        mask <<= n;
        code &= mask;
        code >>>= n;
        return code;
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
