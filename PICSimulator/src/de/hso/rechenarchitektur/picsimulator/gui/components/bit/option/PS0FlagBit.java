package de.hso.rechenarchitektur.picsimulator.gui.components.bit.option;

import de.hso.rechenarchitektur.picsimulator.gui.components.bit.IBitStateChange;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;

public class PS0FlagBit implements IBitStateChange {
    @Override
    public boolean isFlag(RandomAccessMemory ram) {
        return ram.isPS0();
    }

    @Override
    public void setFlag(RandomAccessMemory ram, boolean isActive) {
        ram.setPS0(isActive);
    }
}
