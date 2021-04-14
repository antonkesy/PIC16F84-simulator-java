package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionType;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;
import de.hso.rechenarchitektur.picsimulator.reader.FileReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class PIC16F8XTest {

    public PIC16F8X testPIC;

    public void assertEqualsStatusFlags(boolean isCarry, boolean isDigitCarry, boolean isZero) {
        Assert.assertEquals(testPIC.getRam().isCarryFlag(), isCarry);
        Assert.assertEquals(testPIC.getRam().isDigitCarryFlag(), isDigitCarry);
        Assert.assertEquals(testPIC.getRam().isZeroFlag(), isZero);
    }

    public void assertEqualsInstructionLine(int posInLine, int posInMemory, InstructionType instructionType, int fK, int bD) {
        Assert.assertEquals(new InstructionLine(posInLine, posInMemory, new Instruction(instructionType, fK, bD)), testPIC.getCurrentInstructionInRegister());
        testPIC.step();
    }

    public void assertEqualsWRegister(int expectedValue) {
        Assert.assertEquals(expectedValue, testPIC.getWRegister());
    }

    public void setupPIC(String filePath) {
        FileReader fileReader = new FileReader(new File(filePath));
        testPIC = new PIC16F8X(fileReader.getInstructionLineList());
    }

    @Test
    public void testLST1() {
        setupPIC("LST/TPicSim1.LST");
        //RESET
        Assert.assertEquals(new InstructionLine(), testPIC.getCurrentInstructionInRegister());
        testPIC.step();
        //0

        assertEqualsInstructionLine(18, 0, InstructionType.MOVLW, 17, 0);
        assertEqualsWRegister(0x11);
        //1

        assertEqualsInstructionLine(19, 1, InstructionType.ANDLW, 48, 0);
        assertEqualsWRegister(0x10);
        assertEqualsStatusFlags(false, false, false);
        //2
        assertEqualsInstructionLine(20, 2, InstructionType.IORLW, 13, 0);
        assertEqualsWRegister(0x1D);
        assertEqualsStatusFlags(false, false, false);
        //3
        assertEqualsInstructionLine(21, 3, InstructionType.SUBLW, 61, 0);
        assertEqualsWRegister(0x20);
        assertEqualsStatusFlags(true, true, false);
        //4
        assertEqualsInstructionLine(22, 4, InstructionType.XORLW, 32, 0);
        assertEqualsWRegister(0);
        assertEqualsStatusFlags(true, true, true);
        //5
        assertEqualsInstructionLine(23, 5, InstructionType.ADDLW, 37, 0);
        assertEqualsWRegister(0x25);
        assertEqualsStatusFlags(false, false, false);
        //6
        assertEqualsInstructionLine(27, 6, InstructionType.GOTO, 6, 0);


    }

}
