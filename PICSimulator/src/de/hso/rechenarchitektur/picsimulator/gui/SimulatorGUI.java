package de.hso.rechenarchitektur.picsimulator.gui;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.RandomAccessMemory;
import de.hso.rechenarchitektur.picsimulator.reader.FileReader;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SimulatorGUI {

    private JPanel panelMain;
    private JButton resetButton;
    private JButton startButton;
    private JButton stepButton;
    private JButton stoppButton;
    private JComboBox<ComboBoxItem> quarzBox;
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
    private JTable statusBitTable;
    private JTable intconBitTable;
    private JTable optionBitTable;
    private JCheckBox freigabeWatchdogCheckBox;
    private JLabel watchdogValueLabel;
    private JLabel wValue;
    private JLabel pclValue;
    private JLabel pclathValue;
    private JLabel pcIntern;
    private JLabel statusValue;
    private JLabel fsrValue;
    private JLabel optionValue;
    private JLabel vorteilerValue;
    private JLabel timer0Value;
    private JLabel statusBitText;
    private final JLabel[] stackFields = {stackField0, stackField1, stackField2, stackField3, stackField4, stackField5, stackField6, stackField7};
    //
    private PIC16F8X pic;

    DefaultTableModel modelFileRegister;
    DefaultTableModel modelStatusBits;
    DefaultTableModel modelOptionBits;
    DefaultTableModel modelIntconBits;


    //TODO disable whole UI while non file is selected
    public SimulatorGUI() {

        //Oeffne neue Datei OnClickListener
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
        //Step OnClickListener
        stepButton.addActionListener(e -> {
                    if (pic != null) {
                        //Setzt den Selecter auf die aktuelle Instruktion
                        pic.nextInstruction();
                        updateUIFromPIC();
                    }
                }
        );
        //Reset OnClickListener
        resetButton.addActionListener(e -> {
            if (pic != null) {
                list1.setSelectedIndex(pic.resetCall());
                updateUIFromPIC();
            }
        });
        //Quazbox Listener
        quarzBox.addActionListener(e -> {
            System.out.println("quarz changed " + quarzBox.getSelectedItem().toString());
            if (pic != null) {
                pic.setQuarzSpeed(((ComboBoxItem) quarzBox.getSelectedItem()).getValue());
            }
        });

        updateFileRegister();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PIC Simulator");
        frame.setContentPane(new SimulatorGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setMaximumSize(DimMax);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void updateUIFromPIC() {
        if (pic == null) return;
        list1.setSelectedIndex(pic.getCurrentLine());
        updateStack();
        updateFileRegister();
        updateSFRBits();
        updateSFRW();
    }

    /**
     * Updated Stack Labels
     */
    private void updateStack() {
        int[] stackArray = pic.getStack().getStackArray();
        for (int i = 0; i < stackArray.length; ++i) {
            stackFields[i].setText("" + stackArray[i]);
        }
    }

    private void updateFileRegister() {
        if (pic == null) return;
        fillFRTable(pic.getRam().getDataString());
    }

    private void updateSFRBits() {
        if (pic == null) return;
        //status
        fillModelRowWithData(modelStatusBits, pic.getStatusDataString(), 0);
        //status
        fillModelRowWithData(modelOptionBits, pic.getOptionDataString(), 0);
        //Intcon
        fillModelRowWithData(modelIntconBits, pic.getIntconDataString(), 0);
    }

    private void updateSFRW() {
        if (pic == null) return;
        RandomAccessMemory ram = pic.getRam();
        wValue.setText("" + pic.getWRegister());
        pclValue.setText("" + ram.getPCL());
        pclathValue.setText("" + ram.getPCLath());
        pcIntern.setText("?");
        statusValue.setText("" + ram.getStatus());
        fsrValue.setText("" + ram.getFSR());
        optionValue.setText("" + ram.getOption());
        vorteilerValue.setText("?");
        timer0Value.setText("?");

    }

    private void fillModelRowWithData(DefaultTableModel model, String[] data, int row) {
        for (int i = 0; i < data.length; ++i) {
            model.setValueAt(data[i], row, i);
        }
    }

    private void createUIComponents() {
        //JTable + Model for FLR
        String[] column = {"0x", "+0", "+1", "+2", "+3", "+4", "+5", "+6", "+7"};
        String[][] fileRegisterData = new String[][]{{"e", "m", "p", "t", "y", "", "", "", ""}};
        modelFileRegister = new DefaultTableModel(fileRegisterData, column);
        fileRegisterTable = new JTable(modelFileRegister);
        //Creates empty rows for FLR
        for (int i = 0; i < 15; ++i) {
            modelFileRegister.addRow(new String[][]{{"e", "m", "p", "t", "y", "", "", "", ""}});
        }
        //Status Bits
        column = new String[]{"IRP", "RP1", "RP0", "TO", "PD", "Z", "DC", "C"};
        modelStatusBits = new DefaultTableModel(new String[][]{{"e", "m", "p", "t", "y", "", "", ""}}, column);
        statusBitTable = new JTable(modelStatusBits);
        //Option
        column = new String[]{"RPu", "IEg", "TCs", "TSe", "PSA", "PS2", "PS1", "PS0"};
        modelOptionBits = new DefaultTableModel(new String[][]{{"e", "m", "p", "t", "y", "", "", ""}}, column);
        optionBitTable = new JTable(modelOptionBits);
        //Intcon
        column = new String[]{"GIE", "EIE", "TIE", "IE", "RIE", "TIF", "IF", "RIF"};
        modelIntconBits = new DefaultTableModel(new String[][]{{"e", "m", "p", "t", "y", "", "", ""}}, column);
        intconBitTable = new JTable(modelIntconBits);

        //QuarzSpeedCombobox
        quarzBox = new JComboBox<>();
        //Value in Kilohertz
        quarzBox.addItem(new ComboBoxItem("32 kHz", 32));
        quarzBox.addItem(new ComboBoxItem("100 kHz", 100));
        quarzBox.addItem(new ComboBoxItem("500 kHz", 500));
        quarzBox.addItem(new ComboBoxItem("1 MHz", 1000));
        quarzBox.addItem(new ComboBoxItem("2 MHz", 2000));
        quarzBox.addItem(new ComboBoxItem("4 MHz", 4000));
        quarzBox.addItem(new ComboBoxItem("8 MHz", 8000));
        quarzBox.addItem(new ComboBoxItem("12 MHz", 12000));
        quarzBox.addItem(new ComboBoxItem("16 MHz", 16000));
        quarzBox.addItem(new ComboBoxItem("20 MHz", 20000));

    }


    /**
     * Refills JTableModel with new values
     *
     * @param dataArray
     */
    private void fillFRTable(String[][] dataArray) {
        for (int i = 0; i < dataArray.length; ++i) {
            fillModelRowWithData(modelFileRegister, dataArray[i], i);
        }
    }
}
