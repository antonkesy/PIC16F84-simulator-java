package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.parser.FileReader;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionType;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class LSTReaderTest {
    @Test
    public void testLST0() {
        List<InstructionLine> instructionLineList = new ArrayList<>();
        instructionLineList.add(new InstructionLine(3, 0, new Instruction(InstructionType.MOVLW, 0)));
        instructionLineList.add(new InstructionLine(4, 1, new Instruction(InstructionType.MOVF, 12, 1))); //Nicht sicher wegen 1 fuer w
        instructionLineList.add(new InstructionLine(5, 2, new Instruction(InstructionType.BCF, 8, 4)));
        instructionLineList.add(new InstructionLine(6, 3, new Instruction(InstructionType.BSF, 8, 2)));
        instructionLineList.add(new InstructionLine(7, 4, new Instruction(InstructionType.MOVF, 9)));
        instructionLineList.add(new InstructionLine(8, 5, new Instruction(InstructionType.MOVLW, 170)));
        instructionLineList.add(new InstructionLine(9, 6, new Instruction(InstructionType.MOVF, 9)));
        instructionLineList.add(new InstructionLine(10, 7, new Instruction(InstructionType.BSF, 8, 1)));
        instructionLineList.add(new InstructionLine(12, 8, new Instruction(InstructionType.GOTO, 8)));

        FileReader fileReader = new FileReader(new File("test/LST/TestLST.lst"));

        assertArrayEquals(instructionLineList.toArray(), fileReader.getInstructionLineList().toArray(new InstructionLine[0]));
    }

}
