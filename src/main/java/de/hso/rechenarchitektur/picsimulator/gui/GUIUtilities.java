package de.hso.rechenarchitektur.picsimulator.gui;

import javax.swing.table.DefaultTableModel;

public final class GUIUtilities {

  public static void fillModelRowWithData(DefaultTableModel model, String[] data, int row) {
    for (int i = 0; i < data.length; ++i) {
      model.setValueAt(data[i], row, i);
    }
  }
}
