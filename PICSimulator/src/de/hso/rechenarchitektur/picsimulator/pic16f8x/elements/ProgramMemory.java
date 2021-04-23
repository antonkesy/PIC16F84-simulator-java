package de.hso.rechenarchitektur.picsimulator.pic16f8x.elements;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;

import java.util.List;

public class ProgramMemory {
    private final InstructionLine[] memory;

    public ProgramMemory(List<InstructionLine> instructionLineList) {
        memory = new InstructionLine[1024];
        for (int i = 0; i < instructionLineList.size(); ++i) {
            memory[i] = instructionLineList.get(i);
        }
    }

    public InstructionLine getInstructionAt(int pc) {
        return memory[pc];
    }
}
