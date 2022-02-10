package de.hso.rechenarchitektur.picsimulator.gui.components;

import de.hso.rechenarchitektur.picsimulator.gui.SimulatorGUI;
import de.hso.rechenarchitektur.picsimulator.gui.components.bit.IBitStateChange;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JBitCheckBox for Status, Option and Intcon register
 */
public class JBitCheckBox extends JCheckBox implements ActionListener {

    private final IBitStateChange bitStateChangeInterface;

    private RandomAccessMemory ram;

    private final SimulatorGUI gui;


    public JBitCheckBox(IBitStateChange bitStateChangeInterface, SimulatorGUI gui) {
        this.gui = gui;
        this.bitStateChangeInterface = bitStateChangeInterface;
        addActionListener(this);
    }

    /**
     * Sets RAM for CheckBox
     *
     * @param ram
     */
    public void setRam(RandomAccessMemory ram) {
        this.ram = ram;
    }

    /**
     * Updates if checkBox is selected based on ram value
     */
    public void updateValue() {
        if (ram == null) return;
        setSelected(bitStateChangeInterface.isFlag(ram));
    }

    /**
     * Switches ram bit value on click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (ram == null) return;
        bitStateChangeInterface.setFlag(ram, !bitStateChangeInterface.isFlag(ram));
        gui.updateUIFromPIC();
    }

}
