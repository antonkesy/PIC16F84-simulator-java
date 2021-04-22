package de.hso.rechenarchitektur.picsimulator.gui;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.LSTLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.RandomAccessMemory;
import de.hso.rechenarchitektur.picsimulator.reader.FileReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class SimulatorGUI {

    private JPanel panelMain;
    private JButton resetButton;
    private JButton autorunButton;
    private JButton stepButton;
    private JQuarzComboBox quarzBox;
    private JCheckBox pAp4CheckBox;
    private JCheckBox pAp0CheckBox;
    private JCheckBox pAp2CheckBox;
    private JCheckBox pAp3CheckBox;
    private JCheckBox pAp1CheckBox;
    private JCheckBox pAt7CheckBox;
    private JCheckBox pAt6CheckBox;
    private JCheckBox pAt0CheckBox;
    private JCheckBox pAt1CheckBox;
    private JCheckBox pAt2CheckBox;
    private JCheckBox pAt3CheckBox;
    private JCheckBox pAt4CheckBox;
    private JCheckBox pAt5CheckBox;
    private JCheckBox pBp4CheckBox;
    private JCheckBox pBp0CheckBox;
    private JCheckBox pBp1CheckBox;
    private JCheckBox pBp2CheckBox;
    private JCheckBox pBp3CheckBox;
    private JCheckBox pBt7CheckBox;
    private JCheckBox pBt6CheckBox;
    private JCheckBox pBt0CheckBox;
    private JCheckBox pBt1CheckBox;
    private JCheckBox pBt2CheckBox;
    private JCheckBox pBt3CheckBox;
    private JCheckBox pBt5CheckBox;
    private JCheckBox pBt4CheckBox;
    private JCheckBox pBp5CheckBox;
    private JCheckBox pBp6CheckBox;
    private JCheckBox pBp7CheckBox;
    private JList<Object> lstList;
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
    private JLabel runTimeLabel;
    private JSpinner stepsSpinner;
    private JButton nStepsButton;
    private JScrollPane frScrollPanel;
    private JScrollPane frB1ScrollPanel;
    private FileRegisterTable fileRegisterBank0Table;
    private FileRegisterTable fileRegisterBank1Table;
    private JCheckBox checkBoxIRP;
    private JCheckBox checkBoxRP1;
    private JCheckBox checkBoxRP0;
    private JCheckBox checkBoxTO;
    private JCheckBox checkBoxPD;
    private JCheckBox checkBoxZ;
    private JCheckBox checkBoxDC;
    private JCheckBox checkBoxC;
    private JCheckBox checkBoxRPu;
    private JCheckBox checkBoxIEG;
    private JCheckBox checkBoxTCs;
    private JCheckBox checkBoxTSe;
    private JCheckBox checkBoxPSA;
    private JCheckBox checkBoxPS2;
    private JCheckBox checkBoxPS1;
    private JCheckBox checkBoxPS0;
    private JCheckBox checkBoxGIE;
    private JCheckBox checkBoxEIE;
    private JCheckBox checkBoxTIE;
    private JCheckBox checkBoxIE;
    private JCheckBox checkBoxRIE;
    private JCheckBox checkBoxTIF;
    private JCheckBox checkBoxIF;
    private JCheckBox checkBoxRIF;
    private JPanel frPanel;
    private JLabel statusBitText;
    private final JLabel[] stackFields = {stackField0, stackField1, stackField2, stackField3, stackField4, stackField5, stackField6, stackField7};
    private final JCheckBox[] portAPins = {pAp0CheckBox, pAp1CheckBox, pAp2CheckBox, pAp3CheckBox, pAp4CheckBox};
    private final JCheckBox[] trisA = {pAt0CheckBox, pAt1CheckBox, pAt2CheckBox, pAt3CheckBox, pAt4CheckBox, pAt5CheckBox, pAt6CheckBox, pAt7CheckBox};
    private final JCheckBox[] portBPins = {pBp0CheckBox, pBp1CheckBox, pBp2CheckBox, pBp3CheckBox, pBp4CheckBox, pBp5CheckBox, pBp6CheckBox, pBp7CheckBox};
    private final JCheckBox[] trisB = {pBt0CheckBox, pBt1CheckBox, pBt2CheckBox, pBt3CheckBox, pBt4CheckBox, pBt5CheckBox, pBt6CheckBox, pBt7CheckBox};
    //
    private PIC16F8X pic;

    private boolean isAutoRun;
    private AutoRunThread autoRunThread;

    private List<InstructionLine> lastReadInstructionsLines;

    private List<LSTLine> lstLines;

    private int currentLine;


    //TODO disable whole UI while non file is selected
    public SimulatorGUI() {

        //Oeffne neue Datei OnClickListener
        oeffneNeueDateiButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/LST");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("LST-Files", "LST");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {
                FileReader fileReader = new FileReader(chooser.getSelectedFile());
                lstLines = fileReader.getLineList();
                lstList.setListData(lstLines.toArray());
                lastReadInstructionsLines = fileReader.getInstructionLineList();
                setPIC();
            }
        });
        //Step OnClickListener
        stepButton.addActionListener(e -> step());
        //Reset OnClickListener

        resetButton.addActionListener(e -> {
            if (pic != null) {
                if (isAutoRun)
                    switchAutoRunSimulator();
                setPIC();
            }
        });

        //AutoRun Switch Button
        autorunButton.addActionListener(e -> switchAutoRunSimulator());


        //PortAPin Listener

        Arrays.stream(portAPins).forEach(p -> p.addActionListener(a -> updatePortAPins()));
        Arrays.stream(portBPins).forEach(p -> p.addActionListener(a -> updatePortBPins()));


        updateFileRegister();


        nStepsButton.addActionListener(e -> DoNSteps());

        //Doppelklick auf LSTListe fuer Breakpoints
        lstList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList<String> list = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    // Double-click detected
                    int index = list.locationToIndex(e.getPoint());
                    lstLines.get(index).switchBreakpoint();
                    updateLST();
                }
            }
        });

    }

    private void DoNSteps() {
        int steps = 0;
        try {
            steps = (int) (stepsSpinner.getValue());
        } catch (Exception e) {
            System.out.println("Exception");
        }
        for (int i = 0; i < steps; ++i) {
            stepWithBreakpoints();
        }
    }

    private void setPIC() {
        stepsSpinner.setValue(1000);
        pic = new PIC16F8X(lastReadInstructionsLines);
        fileRegisterBank0Table.setPIC(pic);
        fileRegisterBank1Table.setPIC(pic);
        quarzBox.setPIC(pic);
        lstList.setSelectedIndex(0);
        updateUIFromPIC();
    }

    private int getPinValue(JCheckBox[] pins) {
        int portPinValue = 0;
        for (int i = 0; i < pins.length; ++i) {
            if (pins[i].isSelected()) {
                portPinValue += Math.pow(2, i);
            }
        }
        return portPinValue;
    }

    private void updatePortBPins() {
        pic.getRam().setPortB(getPinValue(portBPins));
        updateUIFromPIC();
    }

    private void updatePortAPins() {
        pic.getRam().setPortA(getPinValue(portAPins));
        updateUIFromPIC();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }

        JFrame frame = new JFrame("PIC Simulator");
        frame.setContentPane(new SimulatorGUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setMaximumSize(DimMax);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    public void updateUIFromPIC() {
        if (pic == null) return;
        currentLine = pic.getCurrentLine();
        lstList.setSelectedIndex(currentLine);
        updateStack();
        updateFileRegister();
        updateSFRBits();
        updateSFRW();
        UpdateTris();
        updateRunTimeLabel();
    }

    private void updateLST() {
        lstList.setListData(lstLines.toArray());
    }

    private void updateRunTimeLabel() {
        runTimeLabel.setText(pic.runTimeToString());
    }

    /**
     * Updated Stack Labels
     */
    private void updateStack() {
        String[] stackStringArray = pic.getStack().getStackStringArray();
        for (int i = 0; i < stackStringArray.length; ++i) {
            stackFields[i].setText(stackStringArray[i]);
        }
    }

    private void UpdateTris() {
        updateTrisA();
        updateTrisB();
    }

    private void updateTrisA() {
        int portAValue = pic.getRam().getTrisA();
        for (int i = 0; i < trisA.length; ++i) {
            trisA[i].setSelected(isBitInPosOne(portAValue, i));
        }
    }

    private void updateTrisB() {
        int portBValue = pic.getRam().getTrisB();
        for (int i = 0; i < trisB.length; ++i) {
            trisB[i].setSelected(isBitInPosOne(portBValue, i));
        }
    }

    private boolean isBitInPosOne(int byteValue, int pos) {
        byteValue >>= pos;
        return (byteValue & 0b1) == 1;
    }

    private void updateFileRegister() {
        if (pic == null) return;
        fileRegisterBank0Table.updateTable();
        fileRegisterBank1Table.updateTable();
    }

    private void updateSFRBits() {
        if (pic == null) return;
        updateStatusCheckBoxes();
        updateOptionCheckBoxes();
        updateIntconCheckBoxes();
    }

    private void updateStatusCheckBoxes() {
        checkBoxIRP.setSelected(pic.getRam().isIRPFlag());
        checkBoxRP1.setSelected(pic.getRam().isRP1());
        checkBoxRP0.setSelected(pic.getRam().isRP0());
        checkBoxTO.setSelected(pic.getRam().isTimeOutFlag());
        checkBoxPD.setSelected(pic.getRam().isPowerDownFlag());
        checkBoxZ.setSelected(pic.getRam().isZeroFlag());
        checkBoxDC.setSelected(pic.getRam().isDigitCarryFlag());
        checkBoxC.setSelected(pic.getRam().isCarryFlag());
    }

    private void updateOptionCheckBoxes() {
        checkBoxRPu.setSelected(pic.getRam().isRPu());
        checkBoxIEG.setSelected(pic.getRam().isIEg());
        checkBoxTCs.setSelected(pic.getRam().isTCs());
        checkBoxTSe.setSelected(pic.getRam().isTSe());
        checkBoxPSA.setSelected(pic.getRam().isPSA());
        checkBoxPS2.setSelected(pic.getRam().isPS2());
        checkBoxPS1.setSelected(pic.getRam().isPS1());
        checkBoxPS0.setSelected(pic.getRam().isPS0());
    }

    private void updateIntconCheckBoxes() {
        checkBoxGIE.setSelected(pic.getRam().isGIE());
        checkBoxEIE.setSelected(pic.getRam().isEIE());
        checkBoxTIE.setSelected(pic.getRam().isTIE());
        checkBoxIE.setSelected(pic.getRam().isIE());
        checkBoxRIE.setSelected(pic.getRam().isRIE());
        checkBoxTIF.setSelected(pic.getRam().isTIF());
        checkBoxIF.setSelected(pic.getRam().isIF());
        checkBoxRIF.setSelected(pic.getRam().isRIF());
    }

    private void updateSFRW() {
        if (pic == null) return;
        RandomAccessMemory ram = pic.getRam();
        wValue.setText("0x" + Integer.toHexString(pic.getWRegister()));
        pclValue.setText("" + ram.getPCL());
        pclathValue.setText("0x" + Integer.toHexString(ram.getPCLath()));
        pcIntern.setText("?");
        statusValue.setText("0x" + Integer.toHexString(ram.getStatus()));
        fsrValue.setText("0x" + Integer.toHexString(ram.getFSR()));
        optionValue.setText("0x" + Integer.toHexString(ram.getOption()));
        vorteilerValue.setText("?");
        timer0Value.setText("?");

    }


    private void createUIComponents() {
        //JTable + Model for FLR
        frScrollPanel = new JScrollPane();
        frB1ScrollPanel = new JScrollPane();
        fileRegisterBank0Table = new FileRegisterTable(frScrollPanel, true, this);
        fileRegisterBank1Table = new FileRegisterTable(frB1ScrollPanel, false, this);
        //QuarzSpeedCombobox
        quarzBox = new JQuarzComboBox();
    }


    private void switchAutoRunSimulator() {
        if (isAutoRun) {
            //TODO
            autoRunThread.stop();
            autoRunThread = null;
        } else {
            //TODO create thread for autorun
            autoRunThread = new AutoRunThread(this);
        }
        isAutoRun = !isAutoRun;
        autorunButton.setText(isAutoRun ? "Stopp" : "Start");
    }

    public void step() {
        if (pic != null) {
            pic.step();
            updateUIFromPIC();
        }
    }

    public void stepWithBreakpoints() {
        if (pic != null) {
            //Ueberprueft ob naechste Instruktion breakpoint ist
            if (isNextInstructionNoBreakpoint()) {
                pic.step();
                updateUIFromPIC();
            }
        }
    }

    public boolean isNextInstructionNoBreakpoint() {
        return currentLine >= 0 && !lstLines.get(currentLine).isBreakpoint();
    }
}
