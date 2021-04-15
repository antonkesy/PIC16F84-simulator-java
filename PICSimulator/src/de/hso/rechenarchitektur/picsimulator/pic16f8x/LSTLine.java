package de.hso.rechenarchitektur.picsimulator.pic16f8x;

/**
 * LSTLine Data Class for setting breakpoints
 */
public class LSTLine {
    private boolean isBreakpoint;
    private final String line;

    public LSTLine(String value) {
        this.line = value;
        this.isBreakpoint = false;
    }

    public boolean isBreakpoint() {
        return isBreakpoint;
    }

    public String getLine() {
        return (isBreakpoint ? "\uD83D\uDD34" : "   ") + line;
    }

    @Override
    public String toString() {
        return getLine();
    }

    public void switchBreakpoint() {
        isBreakpoint = !isBreakpoint;
    }
}
