package de.hso.rechenarchitektur.picsimulator.pic16f8x;

/**
 * Utility KLasse
 * Wandelt den Opcode in Instruktions und Werte um
 */
public final class InstructionDecoder {

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
    public static Instruction byteOrientedInstruction(int opcode) {
        //Special
        switch (opcode) {
            case 0b0000_0110_0100:
                return new Instruction(InstructionType.CLRWDT);
            case 0b0000_0000_1001:
                return new Instruction(InstructionType.RETFIE);
            case 0b0000_0000_1000:
                return new Instruction(InstructionType.RETURN);
            case 0b0000_0110_0011:
                return new Instruction(InstructionType.SLEEP);
        }
        //3rd Byte from right to decide which instruction opcode is
        int instructionCode = opcode & 0b00_1111_0000_0000;
        instructionCode >>>= 8;
        Instruction resultInstruction = null;
        switch (instructionCode) {
            case 0b111:
                //ADDWF
                resultInstruction = new Instruction(InstructionType.ADDWF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b101:
                //ANDWF
                resultInstruction = new Instruction(InstructionType.ANDWF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b1:
                //CLRF & CLRW
                //TODO split and load only necessary
                resultInstruction = new Instruction(is7thBitOne(opcode) ? InstructionType.CLRF : InstructionType.CLRW, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b1001:
                //COMF
                resultInstruction = new Instruction(InstructionType.COMF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b11:
                //DECF
                resultInstruction = new Instruction(InstructionType.DECF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b1011:
                //DECFSZ
                resultInstruction = new Instruction(InstructionType.DECFSZ, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b1010:
                //INCF
                resultInstruction = new Instruction(InstructionType.INCF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b1111:
                //INCFSZ
                resultInstruction = new Instruction(InstructionType.INCFSZ, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b0100:
                //IORWF
                resultInstruction = new Instruction(InstructionType.IORWF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b1000:
                //MOVF
                resultInstruction = new Instruction(InstructionType.MOVF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b0000:
                //MOVWF & NOP
                int opcodeLSBs = opcode & 0b00_0000_1001_1111;
                if (opcodeLSBs == 0) {
                    //NOP
                    resultInstruction = new Instruction(InstructionType.NOP);
                } else {
                    //MOVWF
                    resultInstruction = new Instruction(InstructionType.MOVWF, getByteFs(opcode), get7thBit(opcode));
                }
                break;
            case 0b1101:
                //RLF
                resultInstruction = new Instruction(InstructionType.RLF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b1100:
                //RRF
                resultInstruction = new Instruction(InstructionType.RRF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b0010:
                //SUBWF
                resultInstruction = new Instruction(InstructionType.SUBWF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b1110:
                //SWAPF
                resultInstruction = new Instruction(InstructionType.SWAPF, getByteFs(opcode), get7thBit(opcode));
                break;
            case 0b0110:
                //XORWF
                resultInstruction = new Instruction(InstructionType.XORWF, getByteFs(opcode), get7thBit(opcode));
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
    public static Instruction bitOrientedInstruction(int opcode) {
        //3rd Byte from right to decide which instruction opcode is
        int instructionCode = opcode & 0b00_1100_0000_0000;
        instructionCode >>>= 10;
        InstructionType instructionType = null;
        switch (instructionCode) {
            case 0b0:
                instructionType = InstructionType.BCF;
                break;
            case 0b1:
                instructionType = InstructionType.BSF;
                break;
            case 0b10:
                instructionType = InstructionType.BTFSC;
                break;
            case 0b11:
                instructionType = InstructionType.BTFSS;
                break;
            default:
                System.out.println("Illegal instruction code " + Integer.toBinaryString(instructionCode));
                break;

        }
        return new Instruction(instructionType, getByteFs(opcode), getCodeInRange(opcode, 7, 10));
    }

    /**
     * Gibt LITERAL-ORIENTED FILE REGISTER Instruktion dem opcode entsprechend zurueck
     *
     * @param opcode
     * @return
     */
    public static Instruction literalOrientedInstruction(int opcode) {
        //3rd Byte from right to decide which instruction opcode is
        int instructionCode = opcode & 0b00_1111_0000_0000;
        instructionCode >>>= 8;
        InstructionType instructionType = null;
        switch (instructionCode) {
            case 0b1110:
                instructionType = InstructionType.ADDLW;
                break;
            case 0b1001:
                instructionType = InstructionType.ANDLW;
                break;
            case 0b1000:
                instructionType = InstructionType.IORLW;
                break;
            case 0b0100:
                instructionType = InstructionType.RETLW;
                break;
            case 0b1100:
                instructionType = InstructionType.SUBLW;
                break;
            case 0b1010:
                instructionType = InstructionType.XORLW;
                break;
            case 0b0000:
                instructionType = InstructionType.MOVLW;
                break;
            default:
                System.out.println("Illegal instruction code " + Integer.toBinaryString(instructionCode));
                break;

        }
        return new Instruction(instructionType, getCodeInRange(opcode, 0, 8));
    }


    /**
     * Gibt CONTROL-ORIENTED FILE REGISTER Instruktion dem opcode entsprechend zurueck
     *
     * @param opcode
     * @return
     */
    public static Instruction controlOrientedInstruction(int opcode) {
        InstructionType instructionType = InstructionType.CALL;
        if (getNBit(opcode, 11) == 1) { //
            instructionType = InstructionType.GOTO;
        }
        return new Instruction(instructionType, getCodeInRange(opcode, 0, 11));
    }

    /**
     * Gibt zurueck true, wenn das 7. Bit (von rechts) == 1
     *
     * @param code
     * @return
     */
    public static boolean is7thBitOne(int code) {
        return getNBit(code, 7) == 1;
    }

    /**
     * Gibt das 7. Bit (von rechts) zurueck
     *
     * @param code
     * @return
     */
    public static int get7thBit(int code) {
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
    public static int getNBit(int code, int n) {
        int mask = 1;
        mask <<= n;
        code &= mask;
        code >>>= n;
        return code;
    }

    /**
     * Gibt die Bits in der Range von start bis inclusive end zurueck
     * von rechts nach links
     * Erste ist 0
     *
     * @param code
     * @param startIndex
     * @param endPosition
     * @return
     */
    public static int getCodeInRange(int code, int startIndex, int endPosition) {
        if (startIndex >= endPosition) {
            return -1;
        }
        //Maske fuer die gewuenschten Bits
        int mask = 0;
        mask = ~mask;
        mask >>>= (32 - endPosition + startIndex);
        mask <<= startIndex;
        //
        code &= mask;
        code >>>= startIndex;
        return code;
    }

    /**
     * Helper
     * ByteOriented Instruktions Fs
     *
     * @param code
     * @return
     */
    public static int getByteFs(int code) {
        return getCodeInRange(code, 0, 7);
    }

    //TODO("x cases as fallthrougs in switches")
}
