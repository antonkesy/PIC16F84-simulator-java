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

    public RandomAccessMemory() {
        memory = new int[128][2];
        setStatus(0b0001_1000);
        setTrisA(0b11_1111);
        setTrisB(0b1111_1111);
        setPCL(0);
        setOption(0b1111_1111);
    }

    public enum Bank {
        BANK0, BANK1, BANK2, BANK3
    }

    public int getDataFromAddress(int address) {
        return memory[address][getCurrentBankIndex()];
    }

    public void setDataToAddress(int address, int data) {
        //Wenn im AnwenderBereich, dann wird es gespiegelt
        //TMR0
        switch (address) {
            case 0:
                setIND(data);
            case 1:
                if (isRegisterBank0()) {
                    setTMR0(data);
                } else {
                    setOption(data);
                }
                break;
            case 2:
                setPCL(data);
                break;
            case 3:
                setStatus(data);
                break;
            case 4:
                setFSR(data);
                break;
            case 0xA:
                setPCLath(data);
                break;
            case 0xB:
                setIntcon(data);
                break;
            default:
                memory[address][getCurrentBankIndex()] = data;
        }
    }

    private int getCurrentBankIndex() {
        return isRegisterBank0() ? 0 : 1;
    }

    public int getIND() {
        return getDataFromAddress(0);
    }

    public void setIND(int value) {
        memory[0][0] = value;
        memory[0][1] = value;
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

    public void incrementTMR0By(int value) {
        setTMR0(memory[1][0] + value);
    }

    public void setTMR0(int value) {
        //todo +2, weil es 2 Takte braucht?
        memory[1][0] = value;
        if (memory[1][0] > 0xFF) {
            memory[1][0] = 0;
            setT0IF();
        }
    }

    public int getOption() {
        return memory[1][1];
    }

    public void setOption(int value) {
        memory[1][1] = value;
    }

    public int getJumpAddress(int f) {
        int value = 0;
        //Add pcl to front bits
        int lath2 = getPCLath();
        lath2 >>= 3;
        lath2 &= 0b11;
        value |= lath2;
        //add f (11bits) to value
        value <<= 11;
        return value | f;
    }

    /**
     * PC Low -> unteren 8 Bits
     *
     * @return
     */
    public int getPCL() {
        return getDataFromAddress(2);
    }

    public void setPCL(int value) {
        int newPCLValue = 0;
        newPCLValue |= getPCLath();
        newPCLValue <<= 8;
        newPCLValue |= value;
        memory[2][0] = newPCLValue;
        memory[2][1] = newPCLValue;
    }


    public void incrementPCL() {
        memory[2][0] = memory[2][0] + 1;
        memory[2][1] = memory[2][0] + 1;
    }


    public int getStatus() {
        return memory[3][0];
    }

    public void setStatus(int value) {
        //Gespiegelt?
        memory[3][0] = value;
        memory[3][1] = value;
    }

    public void setStatusBits(
            boolean isCarryFlag,
            boolean isDigitCarry,
            boolean isZeroFlag,
            boolean isPowerDownFlag,
            boolean isTimeOutFlag,
            Bank activeBank,
            boolean isIRP) {
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

        switch (activeBank) {
            case BANK0:
                //nothing
                break;
            case BANK1:
                statusValue += 0b10_0000;
                break;
            case BANK2:
                statusValue += 0b100_0000;
                break;
            case BANK3:
                statusValue += 0b110_0000;
                break;
        }

        if (isIRP) {
            statusValue += 0b1000_0000;
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
        return (getStatus() & 0b10000) == 0b10000;
    }

    public boolean isRegisterBank0() {
        return getCurrentBank() == Bank.BANK0;
    }

    public boolean isRP0() {
        return (getStatus() & 0b10_0000) == 0b10_0000;
    }

    public boolean isRP1() {
        return (getStatus() & 0b100_0000) == 0b100_0000;
    }

    public Bank getCurrentBank() {
        Bank currentBank = Bank.BANK0;
        switch (getStatus() & 0b110_0000) {
            case 0b000_0000:
                //already
                break;
            case 0b010_0000:
                currentBank = Bank.BANK1;
                break;
            case 0b100_0000:
                currentBank = Bank.BANK2;
                break;
            case 0b110_0000:
                currentBank = Bank.BANK3;
                break;
        }
        return currentBank;
    }

    public boolean isIRPFlag() {
        return (getStatus() & 0b1000_0000) == 0b1000_0000;
    }

    public void setCarryFlag(boolean isCarry) {
        setStatusBits(isCarry, isDigitCarryFlag(), isZeroFlag(), isPowerDownFlag(), isTimeOutFlag(), getCurrentBank(), isIRPFlag());
    }

    public void setDigitCarryFlag(boolean isDigitCarry) {
        setStatusBits(isCarryFlag(), isDigitCarry, isZeroFlag(), isPowerDownFlag(), isTimeOutFlag(), getCurrentBank(), isIRPFlag());
    }

    public void setZeroFlag(boolean isZero) {
        setStatusBits(isCarryFlag(), isDigitCarryFlag(), isZero, isPowerDownFlag(), isTimeOutFlag(), getCurrentBank(), isIRPFlag());
    }

    public void setPowerDownFlag(boolean isPowerDownFlag) {
        setStatusBits(isCarryFlag(), isDigitCarryFlag(), isZeroFlag(), isPowerDownFlag, isTimeOutFlag(), getCurrentBank(), isIRPFlag());
    }

    public void setTimeOutFlag(boolean isTimeOutFlag) {
        setStatusBits(isCarryFlag(), isDigitCarryFlag(), isZeroFlag(), isPowerDownFlag(), isTimeOutFlag, getCurrentBank(), isIRPFlag());
    }

    public void setRegisterBank(Bank setBank) {
        setStatusBits(isCarryFlag(), isDigitCarryFlag(), isZeroFlag(), isPowerDownFlag(), isTimeOutFlag(), setBank, isIRPFlag());
    }

    public void setIRPFlag(boolean isIRPFlag) {
        setStatusBits(isCarryFlag(), isDigitCarryFlag(), isZeroFlag(), isPowerDownFlag(), isTimeOutFlag(), getCurrentBank(), isIRPFlag);
    }


    public int getFSR() {
        return getDataFromAddress(4);
    }

    public void setFSR(int value) {
        memory[4][0] = value;
        memory[4][1] = value;
    }

    public int getPortA() {
        return memory[5][0];
    }

    public void setPortA(int value) {
        memory[5][0] = value;
    }

    public int getTrisA() {
        return memory[5][1];
    }

    public void setTrisA(int value) {
        memory[5][1] = value;
    }

    public int getPortB() {
        return memory[6][0];
    }

    public void setPortB(int value) {
        memory[6][0] = value;
    }

    public int getTrisB() {
        return memory[6][1];
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

    /**
     * only 5 bits
     *
     * @return
     */
    public int getPCLath() {
        return (getDataFromAddress(10) & 0b1_1111);
    }

    public void setPCLath(int value) {
        memory[10][0] = value;
        memory[10][1] = value;
    }

    public int getIntcon() {
        return getDataFromAddress(11);
    }

    public void setIntcon(int value) {
        memory[11][0] = value;
        memory[11][1] = value;
    }

    public String[][] getDataString(boolean isFirstBank) {
        String[][] result = new String[8][16];

        int indexX = 0;
        int indexY = 0;
        //Value
        for (int[] bank : memory) {
            result[indexY][indexX] = Integer.toHexString(bank[isFirstBank ? 0 : 1]);
            if (++indexX >= result[indexY].length) {
                indexX = 0;
                ++indexY;
            }
        }

        return result;
    }

    public boolean isRPu() {
        return (getOption() & 0b1000_0000) == 0b1000_0000;
    }

    public boolean isIEg() {
        return (getOption() & 0b100_0000) == 0b100_0000;
    }

    public boolean isTCs() {
        return (getOption() & 0b10_0000) == 0b10_0000;
    }

    public boolean isTSe() {
        return (getOption() & 0b1_0000) == 0b1_0000;
    }

    public boolean isPSA() {
        return (getOption() & 0b1000) == 0b1000;
    }

    public boolean isPS2() {
        return (getOption() & 0b100) == 0b100;
    }

    public boolean isPS1() {
        return (getOption() & 0b10) == 0b10;
    }

    public boolean isPS0() {
        return (getOption() & 0b1) == 0b1;
    }


    public boolean isGIE() {
        return (getIntcon() & 0b1000_0000) == 0b1000_0000;
    }

    public boolean isEIE() {
        return (getIntcon() & 0b100_0000) == 0b100_0000;
    }

    public boolean isTIE() {
        return (getIntcon() & 0b10_0000) == 0b10_0000;
    }

    public boolean isIE() {
        return (getIntcon() & 0b1_0000) == 0b1_0000;
    }

    public boolean isRIE() {
        return (getIntcon() & 0b1000) == 0b1000;
    }

    public boolean isTIF() {
        return (getIntcon() & 0b100) == 0b100;
    }


    public boolean isIF() {
        return (getIntcon() & 0b10) == 0b10;
    }

    public void setT0IF() {
        if ((getIntcon() & 0b10) != 0b10) {
            setIntcon(getIntcon() + 0b10);
        }
    }

    public boolean isRIF() {
        return (getIntcon() & 0b1) == 0b1;
    }


    private String booleanToString(boolean isActive) {
        return isActive ? "1" : "0";
    }
}
