package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionDecoder;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpcodeTest {


    @Test
    public void testBitsInRange() {
        assertEquals(0b10, InstructionDecoder.getCodeInRange(0b1000_1111_0010, 0, 2));
        assertEquals(0b1111, InstructionDecoder.getCodeInRange(0b1000_1111_0010, 4, 8));
        assertEquals(0b1, InstructionDecoder.getCodeInRange(0b1000_1111_0010, 11, 12));
        assertEquals(0b1000_1111_0010, InstructionDecoder.getCodeInRange(0b1000_1111_0010, 0, 13));
        assertEquals(0b1000, InstructionDecoder.getCodeInRange(0b1000_1111_0010, 8, 13));
        assertEquals(-1, InstructionDecoder.getCodeInRange(0b1000_1111_0010, 12, 0));
        assertEquals(0, InstructionDecoder.getCodeInRange(0b01_0000_0000_0000, 7, 9));
    }

    @Test
    public void testInstructionEquals() {
        assertTrue(instructionEquals(InstructionType.ADDWF, 0b00, 0b0, 0b0, 7));
        assertTrue(instructionEquals(InstructionType.ADDWF, 0b00, 0b0, 0b1, 7));
    }

    @Test
    public void testOpcodeADDWF() {
        //d Test
        assertEquals(new Instruction(InstructionType.ADDWF, 0, 0), InstructionDecoder.decodeInstruction(0b00_0111_0000_0000));
        assertEquals(new Instruction(InstructionType.ADDWF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0111_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.ADDWF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_0111_0010_0101));
        assertEquals(new Instruction(InstructionType.ADDWF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_0111_0111_1111));
        assertEquals(new Instruction(InstructionType.ADDWF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_0111_1110_0000));
        assertEquals(new Instruction(InstructionType.ADDWF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_0111_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.ADDWF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.ADDWF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeANDWF() {
        //d Test
        assertEquals(new Instruction(InstructionType.ANDWF, 0, 0), InstructionDecoder.decodeInstruction(0b00_0101_0000_0000));
        assertEquals(new Instruction(InstructionType.ANDWF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0101_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.ANDWF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_0101_0010_0101));
        assertEquals(new Instruction(InstructionType.ANDWF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_0101_0111_1111));
        assertEquals(new Instruction(InstructionType.ANDWF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_0101_1110_0000));
        assertEquals(new Instruction(InstructionType.ANDWF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_0101_1001_0001));
    }

    @Test
    public void testOpcodeCLRF() {
        //d Test
        assertEquals(new Instruction(InstructionType.CLRF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0001_1000_0000));
        assertEquals(new Instruction(InstructionType.CLRF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0001_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.CLRF, 0b010_0101, 1), InstructionDecoder.decodeInstruction(0b00_0001_1010_0101));
        assertEquals(new Instruction(InstructionType.CLRF, 0b111_1111, 1), InstructionDecoder.decodeInstruction(0b00_0001_1111_1111));
        assertEquals(new Instruction(InstructionType.CLRF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_0001_1110_0000));
        assertEquals(new Instruction(InstructionType.CLRF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_0001_1001_0001));
    }

    @Test
    public void testOpcodeCLRW() {
        //d Test
        assertEquals(new Instruction(InstructionType.CLRW, 0, 0), InstructionDecoder.decodeInstruction(0b00_0001_0000_0000));
        assertEquals(new Instruction(InstructionType.CLRW, 0, 0), InstructionDecoder.decodeInstruction(0b00_0001_0000_0000));
        //f
        assertEquals(new Instruction(InstructionType.CLRW, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_0001_0010_0101));
        assertEquals(new Instruction(InstructionType.CLRW, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_0001_0111_1111));
        assertEquals(new Instruction(InstructionType.CLRW, 0b110_0000, 0), InstructionDecoder.decodeInstruction(0b00_0001_0110_0000));
        assertEquals(new Instruction(InstructionType.CLRW, 0b001_0001, 0), InstructionDecoder.decodeInstruction(0b00_0001_0001_0001));
    }

    @Test
    public void testOpcodeCOMF() {
        //d Test
        assertEquals(new Instruction(InstructionType.COMF, 0, 0), InstructionDecoder.decodeInstruction(0b00_1001_0000_0000));
        assertEquals(new Instruction(InstructionType.COMF, 0, 1), InstructionDecoder.decodeInstruction(0b00_1001_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.COMF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_1001_0010_0101));
        assertEquals(new Instruction(InstructionType.COMF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_1001_0111_1111));
        assertEquals(new Instruction(InstructionType.COMF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_1001_1110_0000));
        assertEquals(new Instruction(InstructionType.COMF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_1001_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.COMF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.COMF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeDECF() {
        //d Test
        assertEquals(new Instruction(InstructionType.DECF, 0, 0), InstructionDecoder.decodeInstruction(0b00_0011_0000_0000));
        assertEquals(new Instruction(InstructionType.DECF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0011_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.DECF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_0011_0010_0101));
        assertEquals(new Instruction(InstructionType.DECF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_0011_0111_1111));
        assertEquals(new Instruction(InstructionType.DECF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_0011_1110_0000));
        assertEquals(new Instruction(InstructionType.DECF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_0011_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.COMF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.COMF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeDECFSZ() {
        //d Test
        assertEquals(new Instruction(InstructionType.DECFSZ, 0, 0), InstructionDecoder.decodeInstruction(0b00_1011_0000_0000));
        assertEquals(new Instruction(InstructionType.DECFSZ, 0, 1), InstructionDecoder.decodeInstruction(0b00_1011_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.DECFSZ, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_1011_0010_0101));
        assertEquals(new Instruction(InstructionType.DECFSZ, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_1011_0111_1111));
        assertEquals(new Instruction(InstructionType.DECFSZ, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_1011_1110_0000));
        assertEquals(new Instruction(InstructionType.DECFSZ, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_1011_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.DECFSZ, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.DECFSZ, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeINCF() {
        //d Test
        assertEquals(new Instruction(InstructionType.INCF, 0, 0), InstructionDecoder.decodeInstruction(0b00_1010_0000_0000));
        assertEquals(new Instruction(InstructionType.INCF, 0, 1), InstructionDecoder.decodeInstruction(0b00_1010_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.INCF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_1010_0010_0101));
        assertEquals(new Instruction(InstructionType.INCF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_1010_0111_1111));
        assertEquals(new Instruction(InstructionType.INCF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_1010_1110_0000));
        assertEquals(new Instruction(InstructionType.INCF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_1010_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.INCF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.INCF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeINCFSZ() {
        //d Test
        assertEquals(new Instruction(InstructionType.INCFSZ, 0, 0), InstructionDecoder.decodeInstruction(0b00_1111_0000_0000));
        assertEquals(new Instruction(InstructionType.INCFSZ, 0, 1), InstructionDecoder.decodeInstruction(0b00_1111_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.INCFSZ, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_1111_0010_0101));
        assertEquals(new Instruction(InstructionType.INCFSZ, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_1111_0111_1111));
        assertEquals(new Instruction(InstructionType.INCFSZ, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_1111_1110_0000));
        assertEquals(new Instruction(InstructionType.INCFSZ, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_1111_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.INCFSZ, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.INCFSZ, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeIORWF() {
        //d Test
        assertEquals(new Instruction(InstructionType.IORWF, 0, 0), InstructionDecoder.decodeInstruction(0b00_0100_0000_0000));
        assertEquals(new Instruction(InstructionType.IORWF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0100_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.IORWF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_0100_0010_0101));
        assertEquals(new Instruction(InstructionType.IORWF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_0100_0111_1111));
        assertEquals(new Instruction(InstructionType.IORWF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_0100_1110_0000));
        assertEquals(new Instruction(InstructionType.IORWF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_0100_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.IORWF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.IORWF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeMOVF() {
        //d Test
        assertEquals(new Instruction(InstructionType.MOVF, 0, 0), InstructionDecoder.decodeInstruction(0b00_1000_0000_0000));
        assertEquals(new Instruction(InstructionType.MOVF, 0, 1), InstructionDecoder.decodeInstruction(0b00_1000_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.MOVF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_1000_0010_0101));
        assertEquals(new Instruction(InstructionType.MOVF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_1000_0111_1111));
        assertEquals(new Instruction(InstructionType.MOVF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_1000_1110_0000));
        assertEquals(new Instruction(InstructionType.MOVF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_1000_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.MOVF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.MOVF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeMOVWF() {
        //d always 1
        assertEquals(new Instruction(InstructionType.MOVWF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0000_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.MOVWF, 0b010_0101, 1), InstructionDecoder.decodeInstruction(0b00_0000_1010_0101));
        assertEquals(new Instruction(InstructionType.MOVWF, 0b111_1111, 1), InstructionDecoder.decodeInstruction(0b00_0000_1111_1111));
        assertEquals(new Instruction(InstructionType.MOVWF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_0000_1110_0000));
        assertEquals(new Instruction(InstructionType.MOVWF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_0000_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.MOVF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeNOP() {
        //d always 0
        assertEquals(new Instruction(InstructionType.NOP, 0, 0), InstructionDecoder.decodeInstruction(0b00_0000_0000_0000));
        //x
        assertEquals(new Instruction(InstructionType.NOP, 0, 0), InstructionDecoder.decodeInstruction(0b00_0000_0000_0000));
        assertEquals(new Instruction(InstructionType.NOP, 0, 0), InstructionDecoder.decodeInstruction(0b00_0000_0010_0000));
        assertEquals(new Instruction(InstructionType.NOP, 0, 0), InstructionDecoder.decodeInstruction(0b00_0000_0100_0000));
        assertEquals(new Instruction(InstructionType.NOP, 0, 0), InstructionDecoder.decodeInstruction(0b00_0000_0110_0000));

    }

    @Test
    public void testOpcodeRLF() {
        //d
        assertEquals(new Instruction(InstructionType.RLF, 0, 0), InstructionDecoder.decodeInstruction(0b00_1101_0000_0000));
        assertEquals(new Instruction(InstructionType.RLF, 0, 1), InstructionDecoder.decodeInstruction(0b00_1101_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.RLF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_1101_0010_0101));
        assertEquals(new Instruction(InstructionType.RLF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_1101_0111_1111));
        assertEquals(new Instruction(InstructionType.RLF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_1101_1110_0000));
        assertEquals(new Instruction(InstructionType.RLF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_1101_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.RLF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.RLF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeRRF() {
        //d
        assertEquals(new Instruction(InstructionType.RRF, 0, 0), InstructionDecoder.decodeInstruction(0b00_1100_0000_0000));
        assertEquals(new Instruction(InstructionType.RRF, 0, 1), InstructionDecoder.decodeInstruction(0b00_1100_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.RRF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_1100_0010_0101));
        assertEquals(new Instruction(InstructionType.RRF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_1100_0111_1111));
        assertEquals(new Instruction(InstructionType.RRF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_1100_1110_0000));
        assertEquals(new Instruction(InstructionType.RRF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_1100_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.RRF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.RRF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeSUBWF() {
        //d
        assertEquals(new Instruction(InstructionType.SUBWF, 0, 0), InstructionDecoder.decodeInstruction(0b00_0010_0000_0000));
        assertEquals(new Instruction(InstructionType.SUBWF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0010_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.SUBWF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_0010_0010_0101));
        assertEquals(new Instruction(InstructionType.SUBWF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_0010_0111_1111));
        assertEquals(new Instruction(InstructionType.SUBWF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_0010_1110_0000));
        assertEquals(new Instruction(InstructionType.SUBWF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_0010_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.SUBWF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.SUBWF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeSWAPF() {
        //d
        assertEquals(new Instruction(InstructionType.SWAPF, 0, 0), InstructionDecoder.decodeInstruction(0b00_1110_0000_0000));
        assertEquals(new Instruction(InstructionType.SWAPF, 0, 1), InstructionDecoder.decodeInstruction(0b00_1110_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.SWAPF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_1110_0010_0101));
        assertEquals(new Instruction(InstructionType.SWAPF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_1110_0111_1111));
        assertEquals(new Instruction(InstructionType.SWAPF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_1110_1110_0000));
        assertEquals(new Instruction(InstructionType.SWAPF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_1110_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.SWAPF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.SWAPF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeXORWF() {
        //d
        assertEquals(new Instruction(InstructionType.XORWF, 0, 0), InstructionDecoder.decodeInstruction(0b00_0110_0000_0000));
        assertEquals(new Instruction(InstructionType.XORWF, 0, 1), InstructionDecoder.decodeInstruction(0b00_0110_1000_0000));
        //f
        assertEquals(new Instruction(InstructionType.XORWF, 0b010_0101, 0), InstructionDecoder.decodeInstruction(0b00_0110_0010_0101));
        assertEquals(new Instruction(InstructionType.XORWF, 0b111_1111, 0), InstructionDecoder.decodeInstruction(0b00_0110_0111_1111));
        assertEquals(new Instruction(InstructionType.XORWF, 0b110_0000, 1), InstructionDecoder.decodeInstruction(0b00_0110_1110_0000));
        assertEquals(new Instruction(InstructionType.XORWF, 0b001_0001, 1), InstructionDecoder.decodeInstruction(0b00_0110_1001_0001));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.XORWF, 0b00, i, 0b0, 7));
        }
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.XORWF, 0b00, i, 0b1, 7));
        }
    }

    @Test
    public void testOpcodeBCF() {
        //b
        assertEquals(new Instruction(InstructionType.BCF, 0, 0), InstructionDecoder.decodeInstruction(0b01_0000_0000_0000));
        assertEquals(new Instruction(InstructionType.BCF, 0, 0b111), InstructionDecoder.decodeInstruction(0b01_0011_1000_0000));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            for (int j = 0; j < 0b111; ++j) {
                assertTrue(instructionEquals(InstructionType.BCF, 0b1, i, j, 7));
            }
        }
    }

    @Test
    public void testOpcodeBSF() {
        //b
        assertEquals(new Instruction(InstructionType.BSF, 0, 0), InstructionDecoder.decodeInstruction(0b01_0100_0000_0000));
        assertEquals(new Instruction(InstructionType.BSF, 0, 0b111), InstructionDecoder.decodeInstruction(0b01_0111_1000_0000));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            for (int j = 0; j < 0b111; ++j) {
                assertTrue(instructionEquals(InstructionType.BSF, 0b1, i, j, 7));
            }
        }
    }

    @Test
    public void testOpcodeBTFSC() {
        //b
        assertEquals(new Instruction(InstructionType.BTFSC, 0, 0), InstructionDecoder.decodeInstruction(0b01_1000_0000_0000));
        assertEquals(new Instruction(InstructionType.BTFSC, 0, 0b111), InstructionDecoder.decodeInstruction(0b01_1011_1000_0000));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            for (int j = 0; j < 0b111; ++j) {
                assertTrue(instructionEquals(InstructionType.BTFSC, 0b1, i, j, 7));
            }
        }
    }

    @Test
    public void testOpcodeBTFSS() {
        //b
        assertEquals(new Instruction(InstructionType.BTFSS, 0, 0), InstructionDecoder.decodeInstruction(0b01_1100_0000_0000));
        assertEquals(new Instruction(InstructionType.BTFSS, 0, 0b111), InstructionDecoder.decodeInstruction(0b01_1111_1000_0000));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            for (int j = 0; j < 0b111; ++j) {
                assertTrue(instructionEquals(InstructionType.BTFSS, 0b1, i, j, 7));
            }
        }
    }

    @Test
    public void testOpcodeADDLW() {
        //b
        assertEquals(new Instruction(InstructionType.ADDLW, 0), InstructionDecoder.decodeInstruction(0b11_1110_0000_0000));
        assertEquals(new Instruction(InstructionType.ADDLW, 0b1111_1111), InstructionDecoder.decodeInstruction(0b11_1110_1111_1111));

        //Check all possibilities
        for (int i = 0; i < 0b111_1111; ++i) {
            assertTrue(instructionEquals(InstructionType.ADDLW, 0b11, i));
        }
    }

    /**
     * Generates opcode and Instruktion and checks if equals
     *
     * @param type
     * @param categoryBits
     * @param fk
     * @param bd
     * @param bDStartBitIndex
     * @return
     */
    private boolean instructionEquals(InstructionType type, int categoryBits, int fk, int bd, int bDStartBitIndex) {
        return new Instruction(type, fk, bd).equals(InstructionDecoder.decodeInstruction(generateOpcode(type, categoryBits, fk, bd, bDStartBitIndex)));
    }

    /**
     * Generates opcode and Instruktion and checks if equals
     *
     * @param type
     * @param categoryBits
     * @param fk
     * @param bd
     * @param bDStartBitIndex
     * @return
     */
    private boolean instructionEquals(InstructionType type, int categoryBits, int fk) {
        return new Instruction(type, fk).equals(InstructionDecoder.decodeInstruction(generateOpcode(type, categoryBits, fk, 0, 12 - type.maskLength)));
    }

    /**
     * NOT SAFE TO USE!
     *
     * @param type
     * @param categoryBits
     * @param fk
     * @param bd
     * @param bDStartBitIndex
     * @return
     */
    public int generateOpcode(InstructionType type, int categoryBits, int fk, int bd, int bDStartBitIndex) {
        int opcode = categoryBits;
        opcode <<= type.maskLength;
        opcode |= type.mask;
        opcode <<= 8 - bDStartBitIndex + 4 - type.maskLength;
        opcode |= bd;
        opcode <<= bDStartBitIndex;
        opcode |= fk;
        return opcode;
    }
}
