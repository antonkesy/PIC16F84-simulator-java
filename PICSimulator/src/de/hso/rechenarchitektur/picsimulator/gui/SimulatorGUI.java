package de.hso.rechenarchitektur.picsimulator.gui;

import de.hso.rechenarchitektur.picsimulator.parser.FileReader;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorGUI {

    private JPanel panelMain;
    private JButton resetButton;
    private JButton startButton;
    private JButton stepButton;
    private JButton stoppButton;
    private JComboBox comboBox1;
    private JCheckBox a4CheckBox;
    private JCheckBox a0CheckBox;
    private JCheckBox a3CheckBox1;
    private JCheckBox a3CheckBox;
    private JCheckBox a1CheckBox;
    private JCheckBox a7CheckBox;
    private JCheckBox a6CheckBox;
    private JCheckBox a0CheckBox1;
    private JCheckBox a1CheckBox1;
    private JCheckBox a2CheckBox;
    private JCheckBox a3CheckBox2;
    private JCheckBox a4CheckBox1;
    private JCheckBox a5CheckBox;
    private JCheckBox a4CheckBox2;
    private JCheckBox a0CheckBox2;
    private JCheckBox a1CheckBox2;
    private JCheckBox a2CheckBox1;
    private JCheckBox a3CheckBox3;
    private JCheckBox a7CheckBox1;
    private JCheckBox a6CheckBox1;
    private JCheckBox a0CheckBox3;
    private JCheckBox a1CheckBox3;
    private JCheckBox a2CheckBox2;
    private JCheckBox a3CheckBox4;
    private JCheckBox a5CheckBox1;
    private JCheckBox a4CheckBox3;
    private JCheckBox a5CheckBox2;
    private JCheckBox a6CheckBox2;
    private JCheckBox a7CheckBox2;
    private JTable table2;
    private JList list1;
    private JButton oeffneNeueDateiButton;
    //
    private PIC16F8X pic;


    public SimulatorGUI() {
        //OnClickListener
        oeffneNeueDateiButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("LST-Files", "LST");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {
                FileReader fileReader = new FileReader(chooser.getSelectedFile());
                list1.setListData(fileReader.getLineList().toArray());
                pic = new PIC16F8X(fileReader.getInstructionLineList());
                list1.setSelectedIndex(pic.getCurrentLine());
            }
        });
        //OnClickListener
        stepButton.addActionListener(e -> {
                    if (pic != null)
                        //Setzt den Selecter auf die aktuelle Instruktion
                        list1.setSelectedIndex(pic.nextInstruction());
                }
        );
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PIC Simulator");
        frame.setContentPane(new SimulatorGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
