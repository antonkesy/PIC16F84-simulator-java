package de.hso.rechenarchitektur.picsimulator.pic16f8x.funktionsgruppen;

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
    private int[][] memory;
    private boolean isBank0;

    public RandomAccessMemory() {
        memory = new int[128][2];
        isBank0 = true;
    }

    public int getDataFromAddress(int address) {
        return memory[address][isBank0 ? 0 : 1];
    }

    public void setDataToAddress(int address, int data) {
        memory[address][isBank0 ? 0 : 1] = data;
    }

    public void SetBank0() {
        isBank0 = true;
    }

    public void SetBank1() {
        isBank0 = false;
    }
}
