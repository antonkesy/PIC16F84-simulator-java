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

    private final SimulatorGUI gui;


    public JBitCheckBox(BitCheckBoxCategories category, int index, SimulatorGUI gui) {
        this.index = index;
        this.category = category;
        this.gui = gui;
        setLabel();
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
                        break;
                    case 2:
                        setSelected(ram.isZeroFlag());
                        break;
                    case 3:
                        setSelected(ram.isPowerDownFlag());
                        break;
                    case 4:
                        setSelected(ram.isTimeOutFlag());
                        break;
                    case 5:
                        setSelected(ram.isRP0());
                        break;
                    case 6:
                        setSelected(ram.isRP1());
                        break;
                    case 7:
                        setSelected(ram.isIRPFlag());
                        break;
                }
                break;
            case OPTION:
                switch (index) {
                    case 0:
                        setSelected(ram.isPS0());
                        break;
                    case 1:
                        setSelected(ram.isPS1());
                        break;
                    case 2:
                        setSelected(ram.isPS2());
                        break;
                    case 3:
                        setSelected(ram.isPSA());
                        break;
                    case 4:
                        setSelected(ram.isTSe());
                        break;
                    case 5:
                        setSelected(ram.isTCs());
                        break;
                    case 6:
                        setSelected(ram.isIEg());
                        break;
                    case 7:
                        setSelected(ram.isRPu());
                        break;
                }
                break;
            case INTCON:
                switch (index) {
                    case 0:
                        setSelected(ram.isRIF());
                        break;
                    case 1:
                        setSelected(ram.isIF());
                        break;
                    case 2:
                        setSelected(ram.isTIF());
                        break;
                    case 3:
                        setSelected(ram.isRIE());
                        break;
                    case 4:
                        setSelected(ram.isIE());
                        break;
                    case 5:
                        setSelected(ram.isTIE());
                        break;
                    case 6:
                        setSelected(ram.isEIE());
                        break;
                    case 7:
                        setSelected(ram.isGIE());
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
                        ram.switchRPu();
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
        gui.updateUIFromPIC();
    }

    private void setLabel() {
        String label = "";

        switch (category) {
            case STATUS:
                switch (index) {
                    case 0:
                        label = "C";
                        break;
                    case 1:
                        label = "DC";
                        break;
                    case 2:
                        label = "Z";
                        break;
                    case 3:
                        label = "PD";
                        break;
                    case 4:
                        label = "TO";
                        break;
                    case 5:
                        label = "RP0";
                        break;
                    case 6:
                        label = "RP1";
                        break;
                    case 7:
                        label = "IRP";
                        break;
                }
                break;
            case OPTION:
                switch (index) {
                    case 0:
                        label = "PS0";
                        break;
                    case 1:
                        label = "PS1";
                        break;
                    case 2:
                        label = "PS2";
                        break;
                    case 3:
                        label = "PSA";
                        break;
                    case 4:
                        label = "TSe";
                        break;
                    case 5:
                        label = "TCs";
                        break;
                    case 6:
                        label = "IEG";
                        break;
                    case 7:
                        label = "RPu";
                        break;
                }
                break;
            case INTCON:
                switch (index) {
                    case 0:
                        label = "RIF";
                        break;
                    case 1:
                        label = "IF";
                        break;
                    case 2:
                        label = "TIF";
                        break;
                    case 3:
                        label = "RIE";
                        break;
                    case 4:
                        label = "IE";
                        break;
                    case 5:
                        label = "TIE";
                        break;
                    case 6:
                        label = "EIE";
                        break;
                    case 7:
                        label = "GIE";
                        break;
                }
                break;
        }
        this.setText(label);
    }
}
