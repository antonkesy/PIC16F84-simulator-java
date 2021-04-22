package de.hso.rechenarchitektur.picsimulator.gui;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static de.hso.rechenarchitektur.picsimulator.gui.GUIUtilities.fillModelRowWithData;

public class FileRegisterTable extends JTable implements FocusListener {
    DefaultTableModel modelFileRegisterBank;
    private PIC16F8X pic;
    private final boolean isBank0;

    public FileRegisterTable(PIC16F8X pic, JScrollPane frScrollPanel, boolean isBank0) {
        this.isBank0 = isBank0;
        this.pic = pic;
        addFocusListener(this);

        String[][] fileRegisterData = new String[][]{{"", "", "", "", "", "", "", "", ""}};
        String[] column = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        ListModel<String> lm = new FileRegisterTable.RowHeaderListModel();
        modelFileRegisterBank = new DefaultTableModel(fileRegisterData, column);
        setModel(modelFileRegisterBank);
        JList<String> rowHeader = new JList<String>(lm);
        rowHeader.setCellRenderer(new FileRegisterTable.RowHeaderRenderer(this));
        frScrollPanel.setRowHeaderView(rowHeader);

        //Creates empty rows for FLR Table
        for (int i = 0; i < 7; ++i) {
            modelFileRegisterBank.addRow(new String[][]{{"e", "m", "p", "t", "y", "", "", "", ""}});
        }

    }

    public void updateTable() {
        if (pic == null) return;
        String[][] dataArray = pic.getRam().getDataString(isBank0);
        for (int i = 0; i < dataArray.length; ++i) {
            fillModelRowWithData(modelFileRegisterBank, dataArray[i], i);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (pic == null) return;
        int inputNumber = 0;
        try {
            inputNumber = Integer.decode("0x" + getValueAt(getSelectedRow(), getSelectedColumn()));
            inputNumber &= 0xFF; //Max 8 Bit
        } catch (Exception ignored) {
        }
        pic.getRam().setDataToAddress((getSelectedColumn()) + (getSelectedRow() * 16), inputNumber);
    }

    @Override
    public void focusLost(FocusEvent e) {
        //nothing
    }


    static class RowHeaderListModel extends AbstractListModel<String> {
        String[] strRowHeaders;

        public RowHeaderListModel() {
            strRowHeaders = new String[8];
            for (int i = 0; i < strRowHeaders.length; ++i) {
                strRowHeaders[i] = "   " + Integer.toHexString(16 * i) + "   ";
            }
        }

        public int getSize() {
            return strRowHeaders.length;
        }

        public String getElementAt(int index) {
            return strRowHeaders[index];
        }
    }

    static class RowHeaderRenderer extends JLabel implements ListCellRenderer {
        RowHeaderRenderer(JTable table) {
            JTableHeader tableHeader = table.getTableHeader();
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setHorizontalAlignment(CENTER);
            setForeground(tableHeader.getForeground());
            setOpaque(true);
            setFont(tableHeader.getFont());
        }

        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean fSelected, boolean fCellHasFocus) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    public void setPIC(PIC16F8X pic) {
        this.pic = pic;
    }
}