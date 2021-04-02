package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.Instruction;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionDecoder;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OpcodeTest {


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
    }

}
