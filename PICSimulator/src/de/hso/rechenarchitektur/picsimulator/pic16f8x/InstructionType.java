package de.hso.rechenarchitektur.picsimulator.pic16f8x;

public enum InstructionType {
    ADDWF("addwf"), ANDWF("andwf"), CLRF("clrf"), CLRW("clrw"), COMF("comf"), DECF("decf"), DECFSZ("decfsz"),
    INCF("incf"), INCFSZ("incfsz"), IORWF("iorwf"), MOVF("movf"), MOVWF("movwf"), NOP("nop"), RLF("rlf"), RRF("rrf"),
    SUBWF("subwf"), SWAPF("swapf"), XORWF("xorwf"), BCF("bcf"), BSF("bsf"), BTFSC("btfsc"), BTFSS("btfss"), ADDLW("addlw"),
    ANDLW("andlw"), CALL("call"), CLRWDT("clrwdt"), GOTO("goto"), IORLW("iorlw"), MOVLW("movlw"), RETFIE("retfie"),
    RETLW("retlw"), RETURN("return"), SLEEP("sleep"), SUBLW("sublw"), XORLW("xorlw");

    public final String code;

    private InstructionType(String code) {
        this.code = code;
    }

}
