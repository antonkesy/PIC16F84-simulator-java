package de.hso.rechenarchitektur.picsimulator.pic16f8x.elements;

public class WatchDog {

    private boolean isWDT;
    //in micro sec
    private double watchDogTimer;
    private double watchDogTimerEnd;

    public void addWatchDogTimer(double value) {
        watchDogTimer += value;
    }

    public void resetWatchDogTimer() {
        this.watchDogTimer = 0;
    }

    public double getWatchDogTimer() {
        return watchDogTimer;
    }

    public double getWatchDogTimerEnd() {
        return watchDogTimerEnd;
    }

    public void setWatchDogTimerEnd(float watchDogTimerEnd) {
        this.watchDogTimerEnd = watchDogTimerEnd;
    }

    public boolean isWDT() {
        return isWDT;
    }

    public void setWDT(boolean WDT) {
        isWDT = WDT;
    }

    public boolean isWatchDogOver() {
        return watchDogTimer >= watchDogTimerEnd;
    }

    public void switchActive() {
        isWDT = !isWDT;
    }

    public String getWatchDogTimerString() {
        return String.format("%.3f", getWatchDogTimer()) + "\u00B5s";
    }

    public String getWatchDogTimerEndString() {
        return String.format("%.3f", getWatchDogTimerEnd()) + "\u00B5s";
    }
}
