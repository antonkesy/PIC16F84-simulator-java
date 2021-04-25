package de.hso.rechenarchitektur.picsimulator.gui;

import de.hso.rechenarchitektur.picsimulator.gui.components.JBitCheckBox;
import de.hso.rechenarchitektur.picsimulator.gui.components.JFileRegisterTable;
import de.hso.rechenarchitektur.picsimulator.gui.components.JQuarzComboBox;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.InstructionLine;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.instructions.LSTLine;
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
    private JFileRegisterTable fileRegisterBank0Table;
    private JFileRegisterTable fileRegisterBank1Table;
    private JBitCheckBox checkBoxIRP;
    private JBitCheckBox checkBoxRP1;
    private JBitCheckBox checkBoxRP0;
    private JBitCheckBox checkBoxTO;
    private JBitCheckBox checkBoxPD;
    private JBitCheckBox checkBoxZ;
    private JBitCheckBox checkBoxDC;
    private JBitCheckBox checkBoxC;
    private JBitCheckBox checkBoxRPu;
    private JBitCheckBox checkBoxIEG;
    private JBitCheckBox checkBoxTCs;
    private JBitCheckBox checkBoxTSe;
    private JBitCheckBox checkBoxPSA;
    private JBitCheckBox checkBoxPS2;
    private JBitCheckBox checkBoxPS1;
    private JBitCheckBox checkBoxPS0;
    private JBitCheckBox checkBoxGIE;
    private JBitCheckBox checkBoxEIE;
    private JBitCheckBox checkBoxTIE;
    private JBitCheckBox checkBoxIE;
    private JBitCheckBox checkBoxRIE;
    private JBitCheckBox checkBoxTIF;
    private JBitCheckBox checkBoxIF;
    private JBitCheckBox checkBoxRIF;
    private JButton ignoreButton;
    private JLabel watchDogEndeLabel;
    private JPanel frPanel;
    private JLabel statusBitText;
    private JLabel[] stackFields = {stackField0, stackField1, stackField2, stackField3, stackField4, stackField5, stackField6, stackField7};
    private JCheckBox[] portAPins = {pAp0CheckBox, pAp1CheckBox, pAp2CheckBox, pAp3CheckBox, pAp4CheckBox};
    private JCheckBox[] trisA = {pAt0CheckBox, pAt1CheckBox, pAt2CheckBox, pAt3CheckBox, pAt4CheckBox, pAt5CheckBox, pAt6CheckBox, pAt7CheckBox};
    private JCheckBox[] portBPins = {pBp0CheckBox, pBp1CheckBox, pBp2CheckBox, pBp3CheckBox, pBp4CheckBox, pBp5CheckBox, pBp6CheckBox, pBp7CheckBox};
    private JCheckBox[] trisB = {pBt0CheckBox, pBt1CheckBox, pBt2CheckBox, pBt3CheckBox, pBt4CheckBox, pBt5CheckBox, pBt6CheckBox, pBt7CheckBox};
    //
    private JBitCheckBox[] statusBitCheckBoxes;
    private JBitCheckBox[] optionBitCheckBoxes;
    private JBitCheckBox[] intconBitCheckBoxes;
    private PIC16F8X pic;

    private boolean isAutoRun;
    private AutoRunThread autoRunThread;

    private List<InstructionLine> lastReadInstructionsLines;

    private List<LSTLine> lstLines;

    private int currentLine;


    //TODO disable whole UI while non file is selected
    public SimulatorGUI() {
        //Oeffne neue Datei OnClickListener
        oeffneNeueDateiButton.addActionListener(e -> openNewFile());
        //Step OnClickListener
        stepButton.addActionListener(e -> step());
        //Reset OnClickListener
        resetButton.addActionListener(e -> resetPIC());
        //AutoRun Switch Button
        autorunButton.addActionListener(e -> switchAutoRunSimulator());

        freigabeWatchdogCheckBox.addActionListener(e -> pic.getWatchDog().switchActive());
        //PortAPin Listener
        Arrays.stream(portAPins).forEach(p -> p.addActionListener(a -> portAPinsClickUpdate()));
        Arrays.stream(portBPins).forEach(p -> p.addActionListener(a -> portBPinsClickUpdate()));

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

        ignoreButton.addActionListener(e -> ignoreNextInstruction());

        pAp4CheckBox.addActionListener(e -> pic.switchRA4T0CKI(pAp4CheckBox.isSelected()));
    }


    private void ignoreNextInstruction() {
        pic.skipNextInstruction();
        updateUIFromPIC();
    }

    private void resetPIC() {
        if (pic != null) {
            if (isAutoRun)
                switchAutoRunSimulator();
            setPIC();
        }
    }

    private void openNewFile() {
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
    }

    private void DoNSteps() {
        //TODO stop by reset (and dont reset yet)
        int steps = 0;
        try {
            steps = (int) (stepsSpinner.getValue());
        } catch (Exception e) {
            System.out.println("Exception");
        }
        if (steps > 1) {
            //fist step is normal to skip breakpoint
            step();
            for (int i = 0; i < steps - 1; ++i) {
                stepWithBreakpoints();
            }
        }
    }

    private void setPIC() {
        stepsSpinner.setValue(1000);
        pic = new PIC16F8X(lastReadInstructionsLines);
        fileRegisterBank0Table.setPIC(pic);
        fileRegisterBank1Table.setPIC(pic);
        quarzBox.setPIC(pic);
        lstList.setSelectedIndex(0);
        updateBitCheckBoxesRAM();
        pic.setWDT(freigabeWatchdogCheckBox.isSelected());
        updateUIFromPIC();
    }

    private void updateWatchDogTimer() {
        watchdogValueLabel.setText(pic.getWatchDog().getWatchDogTimerString());
        watchDogEndeLabel.setText(pic.getWatchDog().getWatchDogTimerEndString());
    }

    private void updatePortPins() {
        setPortSelected(portAPins, pic.getRam().getPortA());
        setPortSelected(portBPins, pic.getRam().getPortB());
    }

    private void setPortSelected(JCheckBox[] portPins, int portValue) {
        for (int i = 0; i < portPins.length; ++i) {
            portPins[i].setSelected((portValue >> (i) & 1) == 1);
        }
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

    private void portBPinsClickUpdate() {
        pic.getRam().setPortB(getPinValue(portBPins));
        updateUIFromPIC();
    }

    private void portAPinsClickUpdate() {
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
        lstList.ensureIndexIsVisible(lstList.getSelectedIndex());
        updateStack();
        updateFileRegister();
        updateSFRBits();
        updateSFRW();
        UpdateTris();
        updateRunTimeLabel();
        updatePortPins();
        updateWatchDogTimer();
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
        updateBitCheckBoxes(statusBitCheckBoxes);
        updateBitCheckBoxes(optionBitCheckBoxes);
        updateBitCheckBoxes(intconBitCheckBoxes);
    }

    private void updateBitCheckBoxes(JBitCheckBox[] checkBoxes) {
        for (JBitCheckBox checkBox : checkBoxes) {
            checkBox.updateValue();
        }
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
        stackFields = new JLabel[]{stackField0, stackField1, stackField2, stackField3, stackField4, stackField5, stackField6, stackField7};
        portAPins = new JCheckBox[]{pAp0CheckBox, pAp1CheckBox, pAp2CheckBox, pAp3CheckBox, pAp4CheckBox};
        trisA = new JCheckBox[]{pAt0CheckBox, pAt1CheckBox, pAt2CheckBox, pAt3CheckBox, pAt4CheckBox, pAt5CheckBox, pAt6CheckBox, pAt7CheckBox};
        portBPins = new JCheckBox[]{pBp0CheckBox, pBp1CheckBox, pBp2CheckBox, pBp3CheckBox, pBp4CheckBox, pBp5CheckBox, pBp6CheckBox, pBp7CheckBox};
        trisB = new JCheckBox[]{pBt0CheckBox, pBt1CheckBox, pBt2CheckBox, pBt3CheckBox, pBt4CheckBox, pBt5CheckBox, pBt6CheckBox, pBt7CheckBox};


        //JTable + Model for FLR
        frScrollPanel = new JScrollPane();
        frB1ScrollPanel = new JScrollPane();
        fileRegisterBank0Table = new JFileRegisterTable(frScrollPanel, true, this);
        fileRegisterBank1Table = new JFileRegisterTable(frB1ScrollPanel, false, this);
        //QuarzSpeedCombobox
        quarzBox = new JQuarzComboBox();
        //SFR Bits

        createStatusCheckBoxes();
        createOptionCheckBoxes();
        createIntconCheckBoxes();
    }

    private void createStatusCheckBoxes() {
        checkBoxC = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.STATUS, 0, this);
        checkBoxDC = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.STATUS, 1, this);
        checkBoxZ = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.STATUS, 2, this);
        checkBoxPD = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.STATUS, 3, this);
        checkBoxTO = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.STATUS, 4, this);
        checkBoxRP0 = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.STATUS, 5, this);
        checkBoxRP1 = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.STATUS, 6, this);
        checkBoxIRP = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.STATUS, 7, this);
        statusBitCheckBoxes = new JBitCheckBox[]{checkBoxC, checkBoxDC, checkBoxZ, checkBoxPD, checkBoxTO, checkBoxRP0, checkBoxRP1, checkBoxIRP};
    }


    private void createOptionCheckBoxes() {
        checkBoxPS0 = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.OPTION, 0, this);
        checkBoxPS1 = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.OPTION, 1, this);
        checkBoxPS2 = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.OPTION, 2, this);
        checkBoxPSA = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.OPTION, 3, this);
        checkBoxTSe = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.OPTION, 4, this);
        checkBoxTCs = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.OPTION, 5, this);
        checkBoxIEG = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.OPTION, 6, this);
        checkBoxRPu = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.OPTION, 7, this);
        optionBitCheckBoxes = new JBitCheckBox[]{checkBoxPS0, checkBoxPS1, checkBoxPS2, checkBoxPSA, checkBoxTSe, checkBoxTCs, checkBoxIEG, checkBoxRPu};
    }

    private void createIntconCheckBoxes() {
        checkBoxRIF = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.INTCON, 0, this);
        checkBoxIF = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.INTCON, 1, this);
        checkBoxTIF = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.INTCON, 2, this);
        checkBoxRIE = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.INTCON, 3, this);
        checkBoxIE = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.INTCON, 4, this);
        checkBoxTIE = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.INTCON, 5, this);
        checkBoxEIE = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.INTCON, 6, this);
        checkBoxGIE = new JBitCheckBox(JBitCheckBox.BitCheckBoxCategories.INTCON, 7, this);
        intconBitCheckBoxes = new JBitCheckBox[]{checkBoxRIF, checkBoxIF, checkBoxTIF, checkBoxRIE, checkBoxIE, checkBoxTIE, checkBoxEIE, checkBoxGIE};
    }

    private void setRAMCheckBoxesArray(JBitCheckBox[] checkBoxes) {
        for (JBitCheckBox checkBox : checkBoxes) {
            checkBox.setRam(pic.getRam());
        }
    }

    private void updateBitCheckBoxesRAM() {
        setRAMCheckBoxesArray(statusBitCheckBoxes);
        setRAMCheckBoxesArray(optionBitCheckBoxes);
        setRAMCheckBoxesArray(intconBitCheckBoxes);
    }


    private void switchAutoRunSimulator() {
        if (isAutoRun) {
            autoRunThread.stop();
            autoRunThread = null;
        } else {
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
