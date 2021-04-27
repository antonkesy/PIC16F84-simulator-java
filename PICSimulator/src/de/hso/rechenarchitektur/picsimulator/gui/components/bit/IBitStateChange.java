package de.hso.rechenarchitektur.picsimulator.gui.components.bit;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.elements.RandomAccessMemory;

/**
 * Interface to change bit flags of status,option, intcon register
 */
public interface IBitStateChange {

    boolean isFlag(RandomAccessMemory ram);

    void setFlag(RandomAccessMemory ram, boolean isActive);
}
