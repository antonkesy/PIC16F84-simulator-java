package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.instructions.literal_and_control_operations.ADDLW;
import de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.instructions.register_operations.bit_oriented.BCF;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OpcodeTest {

    @Test
    public void testOpcodeBCF() {
        BCF bcf = new BCF(0b000_0000, 0b000);
        assertEquals(0b01_0000_0000_0000, bcf.getOpcode());
        //
        bcf = new BCF(0b111_1111, 0b111);
        assertEquals(0b01_0011_1111_1111, bcf.getOpcode());
        //
        bcf = new BCF(0b101_0111,0b010);
        assertEquals(0b01_0001_0101_0111, bcf.getOpcode());
    }

    @Test
    public void testOpcodeADDLW() {
        ADDLW addlw = new ADDLW(0b0000_0000);
        //x bleibt immer 0
        assertEquals(0b11_1110_0000_0000, addlw.getOpcode());
                //
         addlw = new ADDLW(0b1111_1111);
        assertEquals(0b11_1110_1111_1111, addlw.getOpcode());
        //
         addlw = new ADDLW(0b1010_1101);
        assertEquals(0b11_1110_1010_1101, addlw.getOpcode());
    }

}
