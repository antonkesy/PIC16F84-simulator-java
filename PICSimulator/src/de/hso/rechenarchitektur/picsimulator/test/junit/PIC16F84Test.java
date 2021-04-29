package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F84;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionType;
import de.hso.rechenarchitektur.picsimulator.reader.FileReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class PIC16F84Test {

    public PIC16F84 testPIC;

    public void assertEqualsCheckReset() {
        Assert.assertEquals(new InstructionLine(), testPIC.getCurrentInstructionInRegister());
        testPIC.cycle();
    }

    /**
     * checks carry, digitCarry & zero flag
     *
     * @param isCarry
     * @param isDigitCarry
     * @param isZero
     */
    public void assertEqualsStatusFlags(boolean isCarry, boolean isDigitCarry, boolean isZero) {
        Assert.assertEquals(testPIC.getRam().isCarryFlag(), isCarry);
        Assert.assertEquals(testPIC.getRam().isDigitCarryFlag(), isDigitCarry);
        Assert.assertEquals(testPIC.getRam().isZeroFlag(), isZero);
    }

    /**
     * Checks custom instruction line
     *
     * @param posInLine
     * @param posInMemory
     * @param instructionType
     * @param fK
     * @param bD
     */
    public void assertEqualsInstructionLine(int posInLine, int posInMemory, InstructionType instructionType, int fK, int bD) {
        Assert.assertEquals(new InstructionLine(posInLine, posInMemory, new Instruction(instructionType, fK, bD)), testPIC.getCurrentInstructionInRegister());
        testPIC.cycle();
    }

    /**
     * checks wRegister
     *
     * @param expectedValue
     */
    public void assertEqualsWRegister(int expectedValue) {
        Assert.assertEquals(expectedValue, testPIC.getWRegister());
    }

    /**
     * Sets up new PIC with File from filePath
     *
     * @param filePath
     */
    public void setupPIC(String filePath) {
        FileReader fileReader = new FileReader(new File(filePath));
        testPIC = new PIC16F84(fileReader.getInstructionLineList());
    }

    @Test
    public void testLST1() {
        setupPIC("LST/TPicSim1.LST");
        //RESET
        assertEqualsCheckReset();
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

    @Test
    public void testLST2() {
        setupPIC("LST/TPicSim2.LST");
        //RESET
        assertEqualsCheckReset();

        for (int i = 0; i < 100; ++i) {
            //0
            assertEqualsInstructionLine(16, 0, InstructionType.MOVLW, 0x11, 0);
            assertEqualsWRegister(0x11);
            assertEqualsStatusFlags(false, false, false);

            //1
            assertEqualsInstructionLine(17, 1, InstructionType.CALL, 0x6, 0);
            assertEqualsWRegister(0x11);
            assertEqualsStatusFlags(false, false, false);
            //2
            assertEqualsInstructionLine(24, 6, InstructionType.ADDLW, 0x25, 0);
            assertEqualsWRegister(0x36);
            assertEqualsStatusFlags(false, false, false);
            //3
            assertEqualsInstructionLine(25, 7, InstructionType.RETURN, 0, 0);
            assertEqualsWRegister(0x36);
            assertEqualsStatusFlags(false, false, false);
            //4
            assertEqualsInstructionLine(18, 2, InstructionType.NOP, 0, 0);
            assertEqualsWRegister(0x36);
            assertEqualsStatusFlags(false, false, false);

            //5
            assertEqualsInstructionLine(19, 3, InstructionType.CALL, 0x8, 0);
            assertEqualsWRegister(0x36);
            assertEqualsStatusFlags(false, false, false);
            //6
            assertEqualsInstructionLine(28, 8, InstructionType.RETLW, 0x77, 0);
            assertEqualsWRegister(0x77);
            assertEqualsStatusFlags(false, false, false);
            //7
            assertEqualsInstructionLine(20, 4, InstructionType.NOP, 0, 0);
            assertEqualsWRegister(0x77);
            assertEqualsStatusFlags(false, false, false);
            //7
            assertEqualsInstructionLine(21, 5, InstructionType.GOTO, 0, 0);
            assertEqualsWRegister(0x77);
            assertEqualsStatusFlags(false, false, false);
        }
    }

    @Test
    public void testLST3() {
        setupPIC("LST/TPicSim3.LST");
        //RESET
        assertEqualsCheckReset();
        //0
        assertEqualsInstructionLine(35, 0, InstructionType.MOVLW, 0x11, 0);
        //InstructionLine{positionLineInFile=35, positionInMemory=0, instruction=Instruction{instructionType=MOVLW, fK= 0x11, bD= 0x0}}
        assertEqualsInstructionLine(36, 1, InstructionType.MOVWF, 0xc, 1);
        //InstructionLine{positionLineInFile=36, positionInMemory=1, instruction=Instruction{instructionType=MOVWF, fK= 0xc, bD= 0x1}}
        assertEqualsInstructionLine(37, 2, InstructionType.MOVLW, 0x14, 0);
        //InstructionLine{positionLineInFile=37, positionInMemory=2, instruction=Instruction{instructionType=MOVLW, fK= 0x14, bD= 0x0}}
        //InstructionLine{positionLineInFile=38, positionInMemory=3, instruction=Instruction{instructionType=ADDWF, fK= 0xc, bD= 0x0}}
        //InstructionLine{positionLineInFile=39, positionInMemory=4, instruction=Instruction{instructionType=ADDWF, fK= 0xc, bD= 0x1}}
        //InstructionLine{positionLineInFile=40, positionInMemory=5, instruction=Instruction{instructionType=ANDWF, fK= 0xc, bD= 0x0}}
        //InstructionLine{positionLineInFile=41, positionInMemory=6, instruction=Instruction{instructionType=MOVWF, fK= 0xd, bD= 0x1}}
        //InstructionLine{positionLineInFile=42, positionInMemory=7, instruction=Instruction{instructionType=CLRF, fK= 0xc, bD= 0x1}}
        //InstructionLine{positionLineInFile=43, positionInMemory=8, instruction=Instruction{instructionType=COMF, fK= 0xd, bD= 0x0}}
        //InstructionLine{positionLineInFile=44, positionInMemory=9, instruction=Instruction{instructionType=DECF, fK= 0xc, bD= 0x0}}
        //InstructionLine{positionLineInFile=45, positionInMemory=10, instruction=Instruction{instructionType=INCF, fK= 0xd, bD= 0x1}}
        //InstructionLine{positionLineInFile=46, positionInMemory=11, instruction=Instruction{instructionType=MOVF, fK= 0xc, bD= 0x1}}
        //InstructionLine{positionLineInFile=47, positionInMemory=12, instruction=Instruction{instructionType=IORWF, fK= 0xc, bD= 0x1}}
        //InstructionLine{positionLineInFile=48, positionInMemory=13, instruction=Instruction{instructionType=SUBWF, fK= 0xd, bD= 0x0}}
        //InstructionLine{positionLineInFile=49, positionInMemory=14, instruction=Instruction{instructionType=SWAPF, fK= 0xd, bD= 0x1}}
        //InstructionLine{positionLineInFile=50, positionInMemory=15, instruction=Instruction{instructionType=XORWF, fK= 0xc, bD= 0x1}}
        //InstructionLine{positionLineInFile=51, positionInMemory=16, instruction=Instruction{instructionType=CLRW, fK= 0x0, bD= 0x0}}
        //InstructionLine{positionLineInFile=53, positionInMemory=17, instruction=Instruction{instructionType=SUBWF, fK= 0xc, bD= 0x0}}
        //InstructionLine{positionLineInFile=54, positionInMemory=18, instruction=Instruction{instructionType=SUBWF, fK= 0xd, bD= 0x0}}
        //InstructionLine{positionLineInFile=55, positionInMemory=19, instruction=Instruction{instructionType=SUBWF, fK= 0xd, bD= 0x1}}
        //InstructionLine{positionLineInFile=56, positionInMemory=20, instruction=Instruction{instructionType=SUBWF, fK= 0xd, bD= 0x1}}
        //InstructionLine{positionLineInFile=60, positionInMemory=21, instruction=Instruction{instructionType=GOTO, fK= 0x15, bD= 0x0}}
    }

}
