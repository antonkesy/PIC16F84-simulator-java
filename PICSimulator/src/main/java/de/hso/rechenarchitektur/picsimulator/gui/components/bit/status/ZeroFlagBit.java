package de.hso.rechenarchitektur.picsimulator.gui.components.bit.status;

import de.hso.rechenarchitektur.picsimulator.gui.components.bit.IBitStateChange;
import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;

public class ZeroFlagBit implements IBitStateChange {

    @Override
    public boolean isFlag(RandomAccessMemory ram) {
        return ram.isZeroFlag();
    }

    @Override
    public void setFlag(RandomAccessMemory ram, boolean isActive) {
        ram.setZeroFlag(isActive);
    }

}