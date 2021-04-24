package de.hso.rechenarchitektur.picsimulator.pic16f8x.elements;

public class Timer {
    public static int getUpdatedTimer(int timer, int cycles, int preScalerValue) {
        return timer + (cycles / preScalerValue);
    }
}
