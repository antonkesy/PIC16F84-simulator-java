package de.hso.rechenarchitektur.picsimulator.pic16f8x;

import java.util.Objects;

/**
 * Verkettung der Instruktion mit der zugehoerigen Line der LTS
 */
public class InstructionLine {

    private final int positionLineInFile;
    private final int positionInMemory;
    private final Instruction instruction;

    /**
     * Konstruktor fuer NOP
     */
    public InstructionLine() {
        positionLineInFile = 0;
        positionInMemory = 0;
        instruction = new Instruction(InstructionType.NOP);
    }

    public InstructionLine(int positionLine, int positionInMemory, Instruction instruction) {
        this.positionLineInFile = positionLine;
        this.instruction = instruction;
        this.positionInMemory = positionInMemory;
    }


    public int getPositionLineInFile() {
        return positionLineInFile;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public int getPositionInMemory() {
        return positionInMemory;
    }

    @Override
    public String toString() {
        return "InstructionLine{" +
                "positionLineInFile=" + positionLineInFile +
                ", positionInMemory=" + positionInMemory +
                ", instruction=" + instruction.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructionLine that = (InstructionLine) o;
        return positionLineInFile == that.positionLineInFile && positionInMemory == that.positionInMemory && Objects.equals(instruction, that.instruction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionLineInFile, positionInMemory, instruction);
    }
}
