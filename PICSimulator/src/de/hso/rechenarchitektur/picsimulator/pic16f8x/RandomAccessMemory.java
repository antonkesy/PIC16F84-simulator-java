package de.hso.rechenarchitektur.picsimulator.pic16f8x;

/**
 * Der Datenspeicher ist in zwei Bänke aufgeteilt. Die SFR
 * sind Register die die Funktionsweise des µCs
 * beeinflussen. In den GPR legt der Programmierer seine
 * Variablen ab.
 */
public class RandomAccessMemory {
    /**
     * Bank0 = [0-127][0] --- Bank1 = [0-127][1]
     */
    private final int[][] memory;
    private boolean isBank0;

    public RandomAccessMemory() {
        memory = new int[128][2];
        isBank0 = true;
        setStatus(0b0001_1000);
        setTrisA(0b11_1111);
        setTrisB(0b1111_1111);
        setPCL(0b0000_0000);
        setOption(0b1111_1111);
    }

    public int getDataFromAddress(int address) {
        return memory[address][getCurrentBankIndex()];
    }

    public void setDataToAddress(int address, int data) {
        //Wenn im AnwenderBereich, dann wird es gespiegelt
        if (address >= 0x0C && address <= 0x2F) {
            memory[address][isBank0 ? 1 : 0] = data;
        }

        memory[address][getCurrentBankIndex()] = data;
    }

    private int getCurrentBankIndex() {
        return isBank0 ? 0 : 1;
    }

    public void SetBank0() {
        isBank0 = true;
    }

    public void SetBank1() {
        isBank0 = false;
    }

    public int getIND() {
        return getDataFromAddress(0);
    }

    public void setIND(int value) {
        setDataToAddress(0, value);
    }

    public int getTMR0() {
        return memory[0][1];
    }

    public void setIndirect(int value) {
        setDataToAddress(0, value);
    }

    public int getIndirect() {
        return getDataFromAddress(0);
    }

    public void setTMR0(int value) {
        memory[1][0] = value;
    }

    public int getOption() {
        return memory[1][1];
    }

    public void setOption(int value) {
        memory[1][1] = value;
    }

    public int getPCL() {
        return getDataFromAddress(2);
    }

    public void setPCL(int value) {
        setDataToAddress(2, value);
    }

    public int getStatus() {
        return getDataFromAddress(3);
    }

    public void setStatus(int value) {
        setDataToAddress(3, value);
    }

    public void setStatusBits(
            boolean isCarryFlag,
            boolean isDigitCarry,
            boolean isZeroFlag,
            boolean isPowerDownFlag,
            boolean isTimeOutFlag,
            boolean isRegisterBank0) {
        int statusValue = 0;
        if (isCarryFlag) {
            statusValue += 0b1;
        }

        if (isDigitCarry) {
            statusValue += 0b10;
        }

        if (isZeroFlag) {
            statusValue += 0b100;
        }

        if (isPowerDownFlag) {
            statusValue += 0b1000;
        }

        if (isTimeOutFlag) {
            statusValue += 0b1_0000;
        }

        if (isRegisterBank0) {
            statusValue += 0b10_0000;
        }

        setStatus(statusValue);

    }

    public boolean isCarryFlag() {
        return (getStatus() & 0b1) == 0b1;
    }

    public boolean isDigitCarryFlag() {
        return (getStatus() & 0b10) == 0b10;
    }

    public boolean isZeroFlag() {
        return (getStatus() & 0b100) == 0b100;
    }

    public boolean isPowerDownFlag() {
        return (getStatus() & 0b1000) == 0b1000;
    }

    public boolean isTimeOutFlag() {
        return (getStatus() & 0b1000) == 0b1000;
    }

    public boolean isRegisterBank0() {
        return (getStatus() & 0b1000) == 0b1000;
    }

    public void setCarryFlag(boolean isCarry) {

    }

    public int getFSR() {
        return getDataFromAddress(4);
    }

    public void setFSR(int value) {
        setDataToAddress(4, value);
    }

    public int getPortA() {
        return memory[0][5];
    }

    public void setPortA(int value) {
        memory[5][0] = value;
    }

    public int getTrisA() {
        return memory[1][5];
    }

    public void setTrisA(int value) {
        memory[5][1] = value;
    }

    public int getPortB() {
        return memory[0][6];
    }

    public void setPortB(int value) {
        memory[6][1] = value;
    }

    public int getTrisB() {
        return memory[1][6];
    }

    public void setTrisB(int value) {
        memory[6][1] = value;
    }

    public void setEEData(int value) {
        memory[8][0] = value;
    }

    public int getEEData() {
        return memory[8][0];
    }

    public void setEECon1(int value) {
        memory[8][1] = value;
    }

    public int getEECon1() {
        return memory[8][1];
    }

    public void setEEAdr(int value) {
        memory[9][0] = value;
    }

    public int getEEAdr() {
        return memory[9][0];
    }

    public void setEECon2(int value) {
        memory[9][1] = value;
    }

    public int getEECon2() {
        return memory[9][1];
    }

    public int getPCLath() {
        return getDataFromAddress(10);
    }

    public void setPCLath(int value) {
        setDataToAddress(10, value);
    }

    public int getIntcon() {
        return getDataFromAddress(11);
    }

    public void setIntcon(int value) {
        setDataToAddress(11, value);
    }

    public String[][] getDataString() {
        String[][] result = new String[16][9];

        int indexX = 1;
        int indexY = 0;
        //Value
        for (int[] bank : memory) {
            result[indexY][indexX] = Integer.toHexString(bank[isBank0 ? 0 : 1]);
            if (++indexX >= result[indexY].length) {
                indexX = 1;
                ++indexY;
            }
        }
        //Header
        for (int i = 0; i < result.length; ++i) {
            result[i][0] = Integer.toHexString(i);
        }

        return result;
    }
}
