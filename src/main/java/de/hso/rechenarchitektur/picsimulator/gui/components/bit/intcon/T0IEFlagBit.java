package de.hso.rechenarchitektur.picsimulator.gui.components.bit.intcon;

import de.hso.rechenarchitektur.picsimulator.gui.components.bit.IBitStateChange;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;

public class T0IEFlagBit implements IBitStateChange {

  @Override
  public boolean isFlag(RandomAccessMemory ram) {
    return ram.isT0IE();
  }

  @Override
  public void setFlag(RandomAccessMemory ram, boolean isActive) {
    ram.setT0IE(isActive);
  }
}
