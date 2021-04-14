package de.hso.rechenarchitektur.picsimulator.gui;

/**
 * ComboBoxItem for QuarzSpeed
 */
public class ComboBoxItem {
    private final String name;
    private final int value;

    public ComboBoxItem(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
