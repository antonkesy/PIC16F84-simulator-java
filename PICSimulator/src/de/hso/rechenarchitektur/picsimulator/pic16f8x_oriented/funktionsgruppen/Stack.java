package de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.funktionsgruppen;

public class Stack {
    private int[] stackArray;
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

}
