package de.hso.rechenarchitektur.picsimulator.pic16f8x;

import jdk.nashorn.internal.objects.annotations.Getter;

public enum InstructionType {
    //x == 0
    ADDWF("addwf", 0b0111), ANDWF("andwf", 0b0101), CLRF("clrf", 0b0001),
    CLRW("clrw", 0b0001), COMF("comf", 0b1001), DECF("decf", 0b0011),
    DECFSZ("decfsz", 0b1011), INCF("incf", 0b1010), INCFSZ("incfsz", 0b1111),
    IORWF("iorwf", 0b0100), MOVF("movf", 0b1000), MOVWF("movwf", 0b0000),
    NOP("nop", 0b0000), RLF("rlf", 0b1101), RRF("rrf", 0b1100),
    SUBWF("subwf", 0b0010), SWAPF("swapf", 0b1110), XORWF("xorwf", 0b0110),
    BCF("bcf", 0b00), BSF("bsf", 0b01), BTFSC("btfsc", 0b10),
    BTFSS("btfss", 0b11), ADDLW("addlw", 0b1110), ANDLW("andlw", 0b1001),
    CALL("call", 0b0), CLRWDT("clrwdt", 0b0000), GOTO("goto", 0b1),
    IORLW("iorlw", 0b1000), MOVLW("movlw", 0b0000), RETFIE("retfie", 0b0000),
    RETLW("retlw", 0b0100), RETURN("return", 0b0000), SLEEP("sleep", 0b0000),
    SUBLW("sublw", 0b1100), XORLW("xorlw", 0b1010);


    public final String code;
    /**
     * 3rd Byte from right opcode
     */
    public final int mask;

    private InstructionType(String code, int mask) {
        this.code = code;
        this.mask = mask;
    }


}
