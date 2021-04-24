package de.hso.rechenarchitektur.picsimulator.pic16f8x.elements;

public class PreScaler {

    public static int getTimerPreScaler(int optionRegisterValue) {
        return (int) Math.pow(2, (optionRegisterValue & 3) + 1);
    }

    public static int getWatchDogPreScaler(int optionRegisterValue) {
        return (int) Math.pow(2, (optionRegisterValue & 3));
    }
}
