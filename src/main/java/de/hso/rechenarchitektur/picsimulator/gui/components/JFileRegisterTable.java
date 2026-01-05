package de.hso.rechenarchitektur.picsimulator.gui.components;

import static de.hso.rechenarchitektur.picsimulator.gui.GUIUtilities.fillModelRowWithData;

import de.hso.rechenarchitektur.picsimulator.gui.SimulatorGUI;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F84;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/** Table with horizontal and vertical headers for file register */
public class JFileRegisterTable extends JTable implements FocusListener {
  DefaultTableModel modelFileRegisterBank;
  private PIC16F84 pic;
  private final boolean isBank0;
  private final SimulatorGUI gui;

  public JFileRegisterTable(JScrollPane frScrollPanel, boolean isBank0, SimulatorGUI gui) {
    this.isBank0 = isBank0;
    this.gui = gui;
    addFocusListener(this);

    // Vertical Header and default values of table
    String[][] fileRegisterData = new String[][] {{"", "", "", "", "", "", "", "", ""}};
    String[] column =
        new String[] {
          "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
        };
    ListModel<String> lm = new JFileRegisterTable.RowHeaderListModel();
    modelFileRegisterBank = new DefaultTableModel(fileRegisterData, column);
    setModel(modelFileRegisterBank);
    JList<String> rowHeader = new JList<String>(lm);
    rowHeader.setCellRenderer(new JFileRegisterTable.RowHeaderRenderer(this));
    frScrollPanel.setRowHeaderView(rowHeader);

    // Creates empty rows for FLR Table
    for (int i = 0; i < 7; ++i) {
      modelFileRegisterBank.addRow(new String[][] {{"e", "m", "p", "t", "y", "", "", "", ""}});
    }
  }

  /** Updates table values without data model */
  public void updateTable() {
    if (pic == null) return;
    String[][] dataArray = pic.getRam().getDataString(isBank0);
    for (int i = 0; i < dataArray.length; ++i) {
      fillModelRowWithData(modelFileRegisterBank, dataArray[i], i);
    }
  }

  @Override
  /** Input new value in table */
  public void focusGained(FocusEvent e) {
    if (pic == null) return;
    int inputNumber = 0;
    try {
      inputNumber = Integer.decode("0x" + getValueAt(getSelectedRow(), getSelectedColumn()));
      inputNumber &= 0xFF; // Max 8 Bit
    } catch (Exception ignored) {
    }
    // Set new value in ram
    pic.getRam().setDataToAddress((getSelectedColumn()) + (getSelectedRow() * 16), inputNumber);
    gui.updateUIFromPIC();
  }

  @Override
  public void focusLost(FocusEvent e) {
    // nothing
  }

  /** ListModel for Vertical header */
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

  /** Vertical header */
  static class RowHeaderRenderer extends JLabel implements ListCellRenderer {
    RowHeaderRenderer(JTable table) {
      JTableHeader tableHeader = table.getTableHeader();
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      setHorizontalAlignment(CENTER);
      setForeground(tableHeader.getForeground());
      setOpaque(true);
      setFont(tableHeader.getFont());
    }

    public Component getListCellRendererComponent(
        JList list, Object value, int index, boolean fSelected, boolean fCellHasFocus) {
      setText((value == null) ? "" : value.toString());
      return this;
    }
  }

  public void setPIC(PIC16F84 pic) {
    this.pic = pic;
  }
}
