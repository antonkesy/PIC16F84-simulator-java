package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionType;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PICSingleInstructionTest {

    PIC16F8X pic;
    List<InstructionLine> instructions;


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
        pic.cycle(); //NOP
        pic.cycle(); //MOVLW
        Assert.assertEquals(testValue, pic.getWRegister());
        pic.cycle(); //MOVWF
        Assert.assertEquals(testValue, pic.getRam().getDataFromAddress(0x20));
        pic.cycle(); //BSF
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
        pic.cycle(); //NOP
        pic.cycle(); //MOVLW
        Assert.assertEquals(testValue, pic.getWRegister());
        pic.cycle(); //MOVWF
        Assert.assertEquals(testValue, pic.getRam().getDataFromAddress(0x20));
        pic.cycle(); //BSF
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
        pic.cycle(); //NOP
        pic.cycle(); //MOVLW
        Assert.assertEquals(testValue, pic.getWRegister());
        pic.cycle(); //ADDLW
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

    public void testDECFSZValues(int testValue, int expectedValue) {
        //possible values
        instructions = new ArrayList<>();
        loadInstructionsWithValueInRegister(testValue, 0x20);
        instructions.add(new InstructionLine(0, 0, new Instruction(InstructionType.DECFSZ, 0x20, 1)));
        instructions.add(new InstructionLine(1, 0, new Instruction(InstructionType.NOP)));
        instructions.add(new InstructionLine(2, 0, new Instruction(InstructionType.NOP)));
        pic = new PIC16F8X(instructions);
        pic.cycle(); //NOP
        pic.cycle(); //MOVLW
        pic.cycle(); //MOVWF
        Assert.assertEquals(testValue, pic.getRam().getDataFromAddress(0x20));
        pic.cycle(); //DECFSZ
        Assert.assertEquals(expectedValue, pic.getRam().getDataFromAddress(0x20));
        //skip if 0
        if (expectedValue == 0) {
            Assert.assertEquals(2, pic.getCurrentInstructionInRegister().getPositionLineInFile());
        }
        ////NOP
    }

    @Test
    public void testDECFSZ() {
        testDECFSZValues(2, 1);
        testDECFSZValues(1, 0);
    }
}
