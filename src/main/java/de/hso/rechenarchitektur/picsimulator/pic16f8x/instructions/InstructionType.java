package de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions;

public enum InstructionType {
  // x == 0
  ADDWF("addwf", 0b0111, 4),
  ANDWF("andwf", 0b0101, 4),
  CLRF("clrf", 0b0001, 4),
  CLRW("clrw", 0b0001, 4),
  COMF("comf", 0b1001, 4),
  DECF("decf", 0b0011, 4),
  DECFSZ("decfsz", 0b1011, 4),
  INCF("incf", 0b1010, 4),
  INCFSZ("incfsz", 0b1111, 4),
  IORWF("iorwf", 0b0100, 4),
  MOVF("movf", 0b1000, 4),
  MOVWF("movwf", 0b0000, 4),
  NOP("nop", 0b0000, 4),
  RLF("rlf", 0b1101, 4),
  RRF("rrf", 0b1100, 4),
  SUBWF("subwf", 0b0010, 4),
  SWAPF("swapf", 0b1110, 4),
  XORWF("xorwf", 0b0110, 4),
  BCF("bcf", 0b00, 2),
  BSF("bsf", 0b1, 2),
  BTFSC("btfsc", 0b10, 2),
  BTFSS("btfss", 0b11, 2),
  ADDLW("addlw", 0b1110, 4),
  ANDLW("andlw", 0b1001, 4),
  CALL("call", 0b0, 1),
  CLRWDT("clrwdt", 0b0000_0110_0100, 12),
  GOTO("goto", 0b1, 1),
  IORLW("iorlw", 0b1000, 4),
  MOVLW("movlw", 0b0000, 2),
  RETFIE("retfie", 0b0000_0000_1001, 12),
  RETLW("retlw", 0b0100, 4),
  RETURN("return", 0b0000_0000_1000, 12),
  SLEEP("sleep", 0b0000_0110_0011, 12),
  SUBLW("sublw", 0b1100, 4),
  XORLW("xorlw", 0b1010, 4);

  public final String code;

  /** 3rd Byte from right opcode */
  public final int mask;

  public final int maskLength;

  private InstructionType(String code, int mask, int maskLength) {
    this.code = code;
    this.mask = mask;
    this.maskLength = maskLength;
  }
}
