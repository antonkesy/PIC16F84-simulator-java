package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionType;
import de.hso.rechenarchitektur.picsimulator.reader.FileReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LSTReaderTest {
    @Test
    public void testLST0() {
        List<InstructionLine> instructionLineList = new ArrayList<>();
        instructionLineList.add(new InstructionLine(18, 0, new Instruction(InstructionType.MOVLW, 0x11)));
        instructionLineList.add(new InstructionLine(19, 1, new Instruction(InstructionType.ANDLW, 0x30)));
        instructionLineList.add(new InstructionLine(20, 2, new Instruction(InstructionType.IORLW, 0x0D)));
        instructionLineList.add(new InstructionLine(21, 3, new Instruction(InstructionType.SUBLW, 0x3D)));
        instructionLineList.add(new InstructionLine(22, 4, new Instruction(InstructionType.XORLW, 0x20)));
        instructionLineList.add(new InstructionLine(23, 5, new Instruction(InstructionType.ADDLW, 0x25)));
        instructionLineList.add(new InstructionLine(27, 6, new Instruction(InstructionType.GOTO, 6)));

        FileReader fileReader = new FileReader(new File("test/LST/TestLST1.LST"));

        Assert.assertEquals(instructionLineList, fileReader.getInstructionLineList());
    }

    @Test
    public void testLST1() {
        List<InstructionLine> instructionLineList = new ArrayList<>();
        instructionLineList.add(new InstructionLine(18, 0, new Instruction(InstructionType.MOVLW, 0x11)));
        instructionLineList.add(new InstructionLine(19, 1, new Instruction(InstructionType.ANDLW, 0x30)));
        instructionLineList.add(new InstructionLine(20, 2, new Instruction(InstructionType.IORLW, 0x0D)));
        instructionLineList.add(new InstructionLine(21, 3, new Instruction(InstructionType.SUBLW, 0x3D)));
        instructionLineList.add(new InstructionLine(22, 4, new Instruction(InstructionType.XORLW, 0x20)));
        instructionLineList.add(new InstructionLine(23, 5, new Instruction(InstructionType.ADDLW, 0x25)));
        instructionLineList.add(new InstructionLine(27, 6, new Instruction(InstructionType.GOTO, 6)));

        FileReader fileReader = new FileReader(new File("LST/TPicSim1.LST"));

        Assert.assertEquals(instructionLineList, fileReader.getInstructionLineList());
    }
}
