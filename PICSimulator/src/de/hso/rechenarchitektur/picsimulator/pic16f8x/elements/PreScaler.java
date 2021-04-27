package de.hso.rechenarchitektur.picsimulator.pic16f8x.elements;

/**
 * PreScaler utility class
 */
public final class PreScaler {
    /**
     * Returns preScaler multiplier for timer0
     *
     * @param optionRegisterValue 8Bit option register value
     * @return multiplier for timer0
     */
    public static int getTimerPreScaler(int optionRegisterValue) {
        return (int) Math.pow(2, (optionRegisterValue & 3) + 1);
    }

    /**
     * Returns preScaler multiplier for watchDog
     *
     * @param optionRegisterValue 8Bit option register value
     * @return multiplier for watchDogs
     */
    public static int getWatchDogPreScaler(int optionRegisterValue) {
        return (int) Math.pow(2, (optionRegisterValue & 3));
    }
}
