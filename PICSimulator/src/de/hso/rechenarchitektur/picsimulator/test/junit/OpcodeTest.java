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
