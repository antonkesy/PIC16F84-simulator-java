package de.hso.rechenarchitektur.picsimulator.pic16f8x;

import java.util.List;

public class ProgramMemory {
    private final InstructionLine[] memory;

    public ProgramMemory(List<InstructionLine> instructionLineList) {
        System.out.println("size" + instructionLineList.size());
        memory = new InstructionLine[1024];
        for (int i = 0; i < instructionLineList.size(); ++i) {
            memory[i] = instructionLineList.get(i);
        }
    }

    public InstructionLine getInstructionAt(int pc) {
        System.out.println(memory[pc].toString());
        return memory[pc];
    }
}
