package de.hso.rechenarchitektur.picsimulator.gui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class FileRegisterTable {

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
}