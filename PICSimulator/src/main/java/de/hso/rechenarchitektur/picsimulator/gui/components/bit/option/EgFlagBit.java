package de.hso.rechenarchitektur.picsimulator.gui.components.bit.option;

import de.hso.rechenarchitektur.picsimulator.gui.components.bit.IBitStateChange;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;

public class EgFlagBit implements IBitStateChange {
    @Override
    public boolean isFlag(RandomAccessMemory ram) {
        return ram.isIEg();
    }

    @Override
    public void setFlag(RandomAccessMemory ram, boolean isActive) {
        ram.setIEg(isActive);
    }

}
