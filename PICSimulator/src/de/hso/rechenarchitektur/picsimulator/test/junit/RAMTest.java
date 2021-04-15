package de.hso.rechenarchitektur.picsimulator.test.junit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.RandomAccessMemory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RAMTest {
    private RandomAccessMemory ram;

    @Before
    public void prepareRAM() {
        ram = new RandomAccessMemory();
    }

    @Test
    public void testRAMReset() {
        RandomAccessMemory ramReset = new RandomAccessMemory();
        Assert.assertEquals(0b0001_1000, ramReset.getStatus());
        Assert.assertEquals(0b11_1111, ramReset.getTrisA());
        Assert.assertEquals(0b1111_1111, ramReset.getTrisB());
        Assert.assertEquals(0b0000_0000, ramReset.getPCL()); //check for PCL
        Assert.assertEquals(0b1111_1111, ramReset.getOption());
    }

    private void setStatusAllFlags(RandomAccessMemory ram) {
        ram.setStatus(0b1111_1111);
    }

    @Test
    public void testStatusAll() {
        RandomAccessMemory ram = new RandomAccessMemory();
        setStatusAllFlags(ram);

        Assert.assertTrue(ram.isCarryFlag());
        Assert.assertTrue(ram.isDigitCarryFlag());
        Assert.assertTrue(ram.isZeroFlag());
        Assert.assertTrue(ram.isPowerDownFlag());
        Assert.assertTrue(ram.isTimeOutFlag());
        Assert.assertFalse(ram.isRegisterBank0());
        Assert.assertTrue(ram.isRP0());
        Assert.assertTrue(ram.isRP1());
        Assert.assertTrue(ram.isIRPFlag());
    }

    @Test
    public void testCarryFlag() {
        setStatusAllFlags(ram);
        ram.setCarryFlag(false);
        Assert.assertFalse(ram.isCarryFlag());
        //
        Assert.assertTrue(ram.isDigitCarryFlag());
        Assert.assertTrue(ram.isZeroFlag());
        Assert.assertTrue(ram.isPowerDownFlag());
        Assert.assertTrue(ram.isTimeOutFlag());
        Assert.assertFalse(ram.isRegisterBank0());
        Assert.assertTrue(ram.isRP0());
        Assert.assertTrue(ram.isRP1());
        Assert.assertTrue(ram.isIRPFlag());
        ram.setCarryFlag(true);
        Assert.assertEquals(0b1111_1111, ram.getStatus());
    }

    @Test
    public void testDigitCarryFlag() {
        setStatusAllFlags(ram);
        ram.setDigitCarryFlag(false);
        Assert.assertFalse(ram.isDigitCarryFlag());
        //
        Assert.assertTrue(ram.isCarryFlag());
        Assert.assertTrue(ram.isZeroFlag());
        Assert.assertTrue(ram.isPowerDownFlag());
        Assert.assertTrue(ram.isTimeOutFlag());
        Assert.assertFalse(ram.isRegisterBank0());
        Assert.assertTrue(ram.isRP0());
        Assert.assertTrue(ram.isRP1());
        Assert.assertTrue(ram.isIRPFlag());
        ram.setDigitCarryFlag(true);
        Assert.assertEquals(0b1111_1111, ram.getStatus());
    }

    @Test
    public void testZeroFlag() {
        setStatusAllFlags(ram);
        ram.setZeroFlag(false);
        Assert.assertFalse(ram.isZeroFlag());
        //
        Assert.assertTrue(ram.isDigitCarryFlag());
        Assert.assertTrue(ram.isCarryFlag());
        Assert.assertTrue(ram.isPowerDownFlag());
        Assert.assertTrue(ram.isTimeOutFlag());
        Assert.assertFalse(ram.isRegisterBank0());
        Assert.assertTrue(ram.isRP0());
        Assert.assertTrue(ram.isRP1());
        Assert.assertTrue(ram.isIRPFlag());
        ram.setZeroFlag(true);
        Assert.assertEquals(0b1111_1111, ram.getStatus());
    }

    @Test
    public void testPDFlag() {
        setStatusAllFlags(ram);
        ram.setPowerDownFlag(false);
        Assert.assertFalse(ram.isPowerDownFlag());
        //
        Assert.assertTrue(ram.isDigitCarryFlag());
        Assert.assertTrue(ram.isZeroFlag());
        Assert.assertTrue(ram.isCarryFlag());
        Assert.assertTrue(ram.isTimeOutFlag());
        Assert.assertFalse(ram.isRegisterBank0());
        Assert.assertTrue(ram.isRP0());
        Assert.assertTrue(ram.isRP1());
        Assert.assertTrue(ram.isIRPFlag());
        ram.setPowerDownFlag(true);
        Assert.assertEquals(0b1111_1111, ram.getStatus());
    }

    @Test
    public void testTOFlag() {
        setStatusAllFlags(ram);
        ram.setTimeOutFlag(false);
        Assert.assertFalse(ram.isTimeOutFlag());
        //
        Assert.assertTrue(ram.isDigitCarryFlag());
        Assert.assertTrue(ram.isZeroFlag());
        Assert.assertTrue(ram.isPowerDownFlag());
        Assert.assertTrue(ram.isCarryFlag());
        Assert.assertFalse(ram.isRegisterBank0());
        Assert.assertTrue(ram.isRP0());
        Assert.assertTrue(ram.isRP1());
        Assert.assertTrue(ram.isIRPFlag());
        ram.setTimeOutFlag(true);
        Assert.assertEquals(0b1111_1111, ram.getStatus());
    }

    @Test
    public void testBankFlag() {
        setStatusAllFlags(ram);
        ram.setRegisterBank(RandomAccessMemory.Bank.BANK0);
        Assert.assertTrue(ram.isRegisterBank0());
        //
        Assert.assertTrue(ram.isDigitCarryFlag());
        Assert.assertTrue(ram.isZeroFlag());
        Assert.assertTrue(ram.isPowerDownFlag());
        Assert.assertTrue(ram.isTimeOutFlag());
        Assert.assertTrue(ram.isCarryFlag());
        ram.setRegisterBank(RandomAccessMemory.Bank.BANK1);
        Assert.assertEquals(0b1011_1111, ram.getStatus());
    }

    @Test
    public void testIRPFlag() {
        setStatusAllFlags(ram);
        ram.setIRPFlag(false);
        Assert.assertFalse(ram.isIRPFlag());
        //
        Assert.assertTrue(ram.isDigitCarryFlag());
        Assert.assertTrue(ram.isZeroFlag());
        Assert.assertTrue(ram.isPowerDownFlag());
        Assert.assertTrue(ram.isCarryFlag());
        Assert.assertTrue(ram.isCarryFlag());
        Assert.assertTrue(ram.isRP0());
        Assert.assertTrue(ram.isRP1());
        Assert.assertFalse(ram.isRegisterBank0());
        ram.setIRPFlag(true);
        Assert.assertEquals(0b1111_1111, ram.getStatus());
    }

    @Test
    public void jumpAddressTest() {
        ram.setPCLath(0b11000);
        Assert.assertEquals(0b11011_0101_0101, ram.getJumpAddress(0b011_0101_0101));
    }
}
