package de.hso.rechenarchitektur.picsimulator.pic16f8x;

public class ProgramMemory {
    private Instruction[] memory;

    public ProgramMemory() {
        memory = new Instruction[1024];
        //todo("read program")
    }

    public Instruction getInstructionAt(int pc) {
        return memory[pc];
    }
}
