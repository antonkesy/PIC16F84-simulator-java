package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionDecoder;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpcodeTest {


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
     * NOT SAFE TO USE!
     *
     * @param type
     * @param categoryBits
     * @param fk
     * @param bd
     * @param bDStartBitIndex
     * @return
     */
    private int generateOpcode(InstructionType type, int categoryBits, int fk, int bd, int bDStartBitIndex) {
        int opcode = categoryBits;
        opcode <<= 4;
        opcode |= type.mask;
        opcode <<= 8 - bDStartBitIndex;
        opcode |= bd;
        opcode <<= bDStartBitIndex;
        opcode |= fk;
        return opcode;
    }
}
