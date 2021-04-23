package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionType;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PICSingleInstructionTest {

    PIC16F8X pic;
    List<InstructionLine> instructions;

    //TODO

    public void loadInstructionsWithValueInRegister(int value, int position) {
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.MOVLW, value)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.MOVWF, position)));
    }

    public void testBitSetFValues(int testValue, int bitPosition, int expectedValue) {
        //possible values
        instructions = new ArrayList<>();
        loadInstructionsWithValueInRegister(testValue, 0x20);
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
        instructions = new ArrayList<>();
        loadInstructionsWithValueInRegister(testValue, 0x20);
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

    public void testBitAddLWValues(int testValue, int addValue, int expectedValue) {
        //possible values
        instructions = new ArrayList<>();
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.MOVLW, testValue)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.ADDLW, addValue)));
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.NOP)));
        pic = new PIC16F8X(instructions);
        pic.step(); //NOP
        pic.step(); //MOVLW
        Assert.assertEquals(testValue, pic.getWRegister());
        pic.step(); //ADDLW
        Assert.assertEquals(expectedValue, pic.getWRegister());
        ////NOP
    }

    @Test
    public void testAddLW() {
        testBitAddLWValues(0, 7, 7);
        testBitAddLWValues(10, 5, 15);
        testBitAddLWValues(200, 0, 200);
        testBitAddLWValues(255, 1, 0);
    }
}
