package de.hso.rechenarchitektur.picsimulator.gui;

/**
 * Thread to autorun PIC Simulator
 */
public class AutoRunThread extends Thread {

    private final SimulatorGUI simulatorGUI;

    public AutoRunThread(SimulatorGUI simulatorGUI) {
        this.simulatorGUI = simulatorGUI;
        this.start();
    }

    @Override
    public void run() {
        while (true) try {
            simulatorGUI.step();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
