package de.hso.rechenarchitektur.picsimulator.gui.components.bit.intcon;

import de.hso.rechenarchitektur.picsimulator.gui.components.bit.IBitStateChange;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;

public class INTEFlagBit implements IBitStateChange {
    @Override
    public boolean isFlag(RandomAccessMemory ram) {
        return ram.isINTE();
    }

    @Override
    public void setFlag(RandomAccessMemory ram, boolean isActive) {
        ram.setINTE(isActive);
    }
}