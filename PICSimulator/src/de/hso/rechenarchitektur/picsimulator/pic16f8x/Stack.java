package de.hso.rechenarchitektur.picsimulator.pic16f8x;

public class Stack {
    private final int[] stackArray;
    private short index;

    public Stack() {
        stackArray = new int[8]; //Todo("startwerte setzen")
        index = 0;
    }

    /**
     * Fuegt eine neue Adresse in den Stack hinzu und setzt den index auf den naechst hoeren Wert
     *
     * @param newAddress
     */
    public void AddNewAddress(int newAddress) {
        if (++index > stackArray.length - 1) {
            index = 0;
        }
        stackArray[index] = newAddress;
    }

    public int[] getStackArray() {
        return stackArray;
    }

    public int pop() {
        return stackArray[index--];
    }
}
