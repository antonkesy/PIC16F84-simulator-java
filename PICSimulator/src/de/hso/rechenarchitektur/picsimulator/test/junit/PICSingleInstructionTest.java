package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionType;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PICSingleInstructionTest {

    PIC16F8X pic;

    //TODO

    public void testBitSetFValues(int testValue, int bitPosition, int expectedValue) {
        //possible values
        List<InstructionLine> instructions = new ArrayList<>();
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.MOVLW, testValue)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.MOVWF, 0x20)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.BSF, 0x20, bitPosition)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.NOP)));
        pic = new PIC16F8X(instructions);
        Assert.assertEquals(0, pic.getRam().getDataFromAddress(0x20));
        pic.step(); //NOP
        pic.step(); //MOVLW
        Assert.assertEquals(testValue, pic.getWRegister());
        pic.step(); //MOVWF
        Assert.assertEquals(testValue, pic.getRam().getDataFromAddress(0x20));
        pic.step(); //BSF
        Assert.assertEquals(expectedValue, pic.getRam().getDataFromAddress(0x20));
        ////NOP
    }

    @Test
    public void testBitSetF() {
        testBitSetFValues(0xA, 7, 0x8A);
        testBitSetFValues(0x8A, 7, 0x8A);
    }

    public void testBitClearFValues(int testValue, int bitPosition, int expectedValue) {
        //possible values
        List<InstructionLine> instructions = new ArrayList<>();
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.MOVLW, testValue)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.MOVWF, 0x20)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.BCF, 0x20, bitPosition)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.NOP)));
        pic = new PIC16F8X(instructions);
        Assert.assertEquals(0, pic.getRam().getDataFromAddress(0x20));
        pic.step(); //NOP
        pic.step(); //MOVLW
        Assert.assertEquals(testValue, pic.getWRegister());
        pic.step(); //MOVWF
        Assert.assertEquals(testValue, pic.getRam().getDataFromAddress(0x20));
        pic.step(); //BSF
        Assert.assertEquals(expectedValue, pic.getRam().getDataFromAddress(0x20));
        ////NOP
    }

    @Test
    public void testBitClearF() {
        testBitClearFValues(0xC7, 7, 0x47);
        testBitClearFValues(0b0010, 0, 0b0010);
        testBitClearFValues(0b0001, 0, 0);
    }
}
