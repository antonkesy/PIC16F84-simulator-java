package de.hso.rechenarchitektur.picsimulator.gui;

import javax.swing.*;

public class SimulatorGUI {

    private JPanel panelMain;

    public SimulatorGUI(){
        panelMain = new JPanel(); //Overwrites UI Designer
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PIC Simulator");
        frame.setContentPane(new SimulatorGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
