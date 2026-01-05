package de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions;

import java.util.Objects;

public class Instruction {
  private final InstructionType instructionType;
  private int fK;
  private int bD;

  public Instruction(InstructionType instructionType) {
    this.instructionType = instructionType;
  }

  public Instruction(InstructionType instructionType, int fk) {
    this(instructionType);
    this.fK = fk;
  }

  public Instruction(InstructionType instructionType, int fk, int bD) {
    this(instructionType, fk);
    this.bD = bD;
  }

  public InstructionType getType() {
    return instructionType;
  }

  public int getFK() {
    return fK;
  }

  public int getBD() {
    return bD;
  }

  @Override
  public String toString() {
    return "Instruction{"
        + "instructionType="
        + instructionType
        + ", fK= 0x"
        + Integer.toHexString(fK)
        + ", bD= 0x"
        + Integer.toHexString(bD)
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Instruction that = (Instruction) o;
    return fK == that.fK && bD == that.bD && instructionType == that.instructionType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(instructionType, fK, bD);
  }
}
