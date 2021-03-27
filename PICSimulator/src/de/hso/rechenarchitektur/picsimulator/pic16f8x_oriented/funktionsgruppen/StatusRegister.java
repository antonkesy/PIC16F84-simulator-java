package de.hso.rechenarchitektur.picsimulator.pic16f8x_oriented.funktionsgruppen;

/**
 * Ist das Ergebnis einer Verknüpfung Null, wird das Zero-Flag
 * gesetzt. Das Carry- und Digitcarry-Flag zeigen einen
 * Bereichsüberlauf an. Der Zahlenbereich bei 8-Bits umfasst die
 * Werte 0 bis 255.
 * Vorsicht: Carry bei ADD, Carry (=Borrow) bei SUB
 */
public class StatusRegister {
    private boolean carryFlag, digitCarryFlag, zeroFlag;

    public boolean isCarryFlag() {
        return carryFlag;
    }

    public void setCarryFlag(boolean value) {
        carryFlag = value;
    }

    public void resetCarryFlag() {
        carryFlag = false;
    }

    public boolean isDigitCarryFlag() {
        return digitCarryFlag;
    }

    public void setDigitCarryFlag(boolean value) {
        digitCarryFlag = value;
    }

    public void resetDigitCarryFlag() {
        digitCarryFlag = false;
    }

    public boolean isZeroFlag() {
        return zeroFlag;
    }

    public void setZeroFlag(boolean value) {
        zeroFlag = value;
    }

    public void resetZeroFlag() {
        zeroFlag = false;
    }
}
