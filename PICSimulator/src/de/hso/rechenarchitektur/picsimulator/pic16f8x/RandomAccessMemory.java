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
}
