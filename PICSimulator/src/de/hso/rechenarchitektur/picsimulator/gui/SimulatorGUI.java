package de.hso.rechenarchitektur.picsimulator.gui;

import de.hso.rechenarchitektur.picsimulator.reader.FileReader;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
    private JTable fileRegisterTable;
    private JList list1;
    private JButton oeffneNeueDateiButton;
    private JLabel stackField0;
    private JLabel stackField1;
    private JLabel stackField2;
    private JLabel stackField3;
    private JLabel stackField4;
    private JLabel stackField5;
    private JLabel stackField6;
    private JLabel stackField7;
    private final JLabel[] stackFields = {stackField0, stackField1, stackField2, stackField3, stackField4, stackField5, stackField6, stackField7};
    //
    private PIC16F8X pic;

    private String[][] fileRegisterData;

    TableModel model;


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
                updateUIFromPIC();
            }
        });
        //OnClickListener
        stepButton.addActionListener(e -> {
                    if (pic != null) {
                        //Setzt den Selecter auf die aktuelle Instruktion
                        pic.nextInstruction();
                        updateUIFromPIC();
                    }
                }
        );
        //OnClickListener
        resetButton.addActionListener(e -> {
            if (pic != null)
                list1.setSelectedIndex(pic.resetCall());
        });

        updateFileRegister();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PIC Simulator");
        frame.setContentPane(new SimulatorGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void updateUIFromPIC() {
        if (pic == null) return;
        list1.setSelectedIndex(pic.getCurrentLine());
        updateStack();
        updateFileRegister();
    }

    private void updateStack() {
        int[] stackArray = pic.getStack().getStackArray();
        for (int i = 0; i < stackArray.length; ++i) {
            stackFields[i].setText("" + stackArray[i]);
        }
    }

    private void updateFileRegister() {
        System.out.println("update File Register");
        fileRegisterData = pic == null ? new String[][]{{"s", "t", "i", "l", "l", "", "N", "U", "L"}} : pic.getRam().getDataString();
        fileRegisterTable.repaint();
        model.setValueAt("a", 0, 0);
        //TODO("set Value for every row/col")
    }

    private void createUIComponents() {
        //TODO fixed headers
        String[] column = {"0x", "+0", "+1", "+2", "+3", "+4", "+5", "+6", "+7"};
        fileRegisterData = pic == null ? new String[][]{{"e", "m", "p", "t", "y", "", "", "", ""}} : pic.getRam().getDataString();
        model = new DefaultTableModel(fileRegisterData, column);
        fileRegisterTable = new JTable(model);
    }
}
