package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.ArithmeticLogicUnit;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.RandomAccessMemory;
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
    public void testADD() {
        Assert.assertEquals(0x25, ArithmeticLogicUnit.add(ram, 0, 0x25));
        //TODO check status flags
        Assert.assertEquals(0x25, ArithmeticLogicUnit.add(ram, 0x25, 0));
        //TODO check status flags
        Assert.assertEquals(0xFF, ArithmeticLogicUnit.add(ram, 0xF0, 0xF));
        //TODO check status flags
        Assert.assertEquals(0x0, ArithmeticLogicUnit.add(ram, 0xF1, 0xF));
    }

    @Test
    public void testSUB() {
        Assert.assertEquals(5, ArithmeticLogicUnit.sub(ram, 10, 5));
        //TODO check status flags
        Assert.assertEquals(0, ArithmeticLogicUnit.sub(ram, 255, 255));
        //TODO check status flags
        Assert.assertEquals(0xFF, ArithmeticLogicUnit.sub(ram, 0, 1));
        //TODO check status flags
        Assert.assertEquals(0x0, ArithmeticLogicUnit.sub(ram, 0, 0));
    }
}