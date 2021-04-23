package de.hso.rechenarchitektur.picsimulator.pic16f8x.elements;

public class Stack {
    private final int[] stackArray;
    private short index;

    public Stack() {
        stackArray = new int[8];
        index = 0;
    }

    /**
     * Fuegt eine neue Adresse in den Stack hinzu und setzt den index auf den naechst hoeren Wert
     *
     * @param newAddress
     */
    public void push(int newAddress) {
        if (++index > stackArray.length - 1) {
            index = 0;
        }
        stackArray[index] = newAddress;
    }

    public int pop() {
        return stackArray[index--];
    }

    /**
     * Returns Stack as String Array filled with leading zeros
     *
     * @return
     */
    public String[] getStackStringArray() {
        String[] stackStringArray = new String[8];
        for (int i = 0; i < stackArray.length; ++i) {
            stackStringArray[i] = ("0000" + stackArray[i]).substring(Integer.toString(stackArray[i]).length());
        }
        return stackStringArray;
    }
}
