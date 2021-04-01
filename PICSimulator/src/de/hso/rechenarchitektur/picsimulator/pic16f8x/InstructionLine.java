package de.hso.rechenarchitektur.picsimulator.pic16f8x;

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
}
