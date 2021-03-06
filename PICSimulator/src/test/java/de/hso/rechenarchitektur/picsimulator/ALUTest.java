package de.hso.rechenarchitektur.picsimulator;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.ArithmeticLogicUnit;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ALUTest {
    public RandomAccessMemory ram;

    @Before
    public void setUpTests() {
        ram = new RandomAccessMemory();
    }

    @Test
    public void testIsCarry() {
        Assert.assertFalse(ArithmeticLogicUnit.isCarry(250, 5));
        Assert.assertTrue(ArithmeticLogicUnit.isCarry(255, 1));
    }

    @Test
    public void testIsDigitCarry() {
        Assert.assertFalse(ArithmeticLogicUnit.isDigitCarry(241, 14));
        Assert.assertTrue(ArithmeticLogicUnit.isDigitCarry(241, 15));
    }

    @Test
    public void testADD() {
        Assert.assertEquals(0x25, ArithmeticLogicUnit.add(ram, 0, 0x25));
        Assert.assertEquals(0x25, ArithmeticLogicUnit.add(ram, 0x25, 0));
        Assert.assertEquals(0xFF, ArithmeticLogicUnit.add(ram, 0xF0, 0xF));
        Assert.assertEquals(0x0, ArithmeticLogicUnit.add(ram, 0xF1, 0xF));
    }

    @Test
    public void testSUB() {
        Assert.assertEquals(5, ArithmeticLogicUnit.sub(ram, 10, 5));
        Assert.assertEquals(0, ArithmeticLogicUnit.sub(ram, 255, 255));
        Assert.assertEquals(0xFF, ArithmeticLogicUnit.sub(ram, 0, 1));
        Assert.assertEquals(0x0, ArithmeticLogicUnit.sub(ram, 0, 0));
        Assert.assertEquals(0x20, ArithmeticLogicUnit.sub(ram, 0x3D, 0x1D)); //61-29 = 32
    }
}
