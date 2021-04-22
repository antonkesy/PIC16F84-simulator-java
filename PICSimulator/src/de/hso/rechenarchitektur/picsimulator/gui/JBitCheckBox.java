package de.hso.rechenarchitektur.picsimulator.gui;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.RandomAccessMemory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JBitCheckBox extends JCheckBox implements ActionListener {

    public enum BitCheckBoxCategories {STATUS, OPTION, INTCON}

    private final BitCheckBoxCategories category;
    private final int index;

    private RandomAccessMemory ram;


    public JBitCheckBox(String label, BitCheckBoxCategories category, int index) {
        this.setText(label);
        this.index = index;
        this.category = category;
        addActionListener(this);
    }

    public void setRam(RandomAccessMemory ram) {
        this.ram = ram;
    }

    public void updateValue() {
        if (ram == null) return;
        switch (category) {
            case STATUS:
                switch (index) {
                    case 0:
                        setSelected(ram.isCarryFlag());
                        break;
                    case 1:
                        setSelected(ram.isDigitCarryFlag());
                        //TODO selectedValue from ram
                        break;
                    case 2:
                        ram.switchZero();
                        break;
                    case 3:
                        ram.switchPD();
                        break;
                    case 4:
                        ram.switchTO();
                        break;
                    case 5:
                        ram.switchRP0();
                        break;
                    case 6:
                        ram.switchRP1();
                        break;
                    case 7:
                        ram.switchIRP();
                        break;
                }
                break;
            case OPTION:
                switch (index) {
                    case 0:
                        ram.switchPS0();
                        break;
                    case 1:
                        ram.switchPS1();
                        break;
                    case 2:
                        ram.switchPS2();
                        break;
                    case 3:
                        ram.switchPSA();
                        break;
                    case 4:
                        ram.switchTSe();
                        break;
                    case 5:
                        ram.switchTCs();
                        break;
                    case 6:
                        ram.switchIEG();
                        break;
                    case 7:
                        ram.switchRPU();
                        break;
                }
                break;
            case INTCON:
                switch (index) {
                    case 0:
                        ram.switchRIF();
                        break;
                    case 1:
                        ram.switchIF();
                        break;
                    case 2:
                        ram.switchTIF();
                        break;
                    case 3:
                        ram.switchRIE();
                        break;
                    case 4:
                        ram.switchIE();
                        break;
                    case 5:
                        ram.switchTIE();
                        break;
                    case 6:
                        ram.switchEIE();
                        break;
                    case 7:
                        ram.switchGIE();
                        break;
                }
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (ram == null) return;
        switch (category) {
            case STATUS:
                switch (index) {
                    case 0:
                        ram.switchCarryFlag();
                        break;
                    case 1:
                        ram.switchDigitCarryFlag();
                        break;
                    case 2:
                        ram.switchZero();
                        break;
                    case 3:
                        ram.switchPD();
                        break;
                    case 4:
                        ram.switchTO();
                        break;
                    case 5:
                        ram.switchRP0();
                        break;
                    case 6:
                        ram.switchRP1();
                        break;
                    case 7:
                        ram.switchIRP();
                        break;
                }
                break;
            case OPTION:
                switch (index) {
                    case 0:
                        ram.switchPS0();
                        break;
                    case 1:
                        ram.switchPS1();
                        break;
                    case 2:
                        ram.switchPS2();
                        break;
                    case 3:
                        ram.switchPSA();
                        break;
                    case 4:
                        ram.switchTSe();
                        break;
                    case 5:
                        ram.switchTCs();
                        break;
                    case 6:
                        ram.switchIEG();
                        break;
                    case 7:
                        ram.switchRPU();
                        break;
                }
                break;
            case INTCON:
                switch (index) {
                    case 0:
                        ram.switchRIF();
                        break;
                    case 1:
                        ram.switchIF();
                        break;
                    case 2:
                        ram.switchTIF();
                        break;
                    case 3:
                        ram.switchRIE();
                        break;
                    case 4:
                        ram.switchIE();
                        break;
                    case 5:
                        ram.switchTIE();
                        break;
                    case 6:
                        ram.switchEIE();
                        break;
                    case 7:
                        ram.switchGIE();
                        break;
                }
                break;
        }
    }
}
