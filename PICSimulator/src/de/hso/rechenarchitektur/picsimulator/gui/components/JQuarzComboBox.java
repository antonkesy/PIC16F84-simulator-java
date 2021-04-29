package de.hso.rechenarchitektur.picsimulator.gui.components;

import de.hso.rechenarchitektur.picsimulator.pic16f8x.PIC16F8X;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class JQuarzComboBox extends JComboBox<JQuarzComboBox.ComboBoxItem> implements ActionListener {

    private PIC16F8X pic;

    public JQuarzComboBox() {
        //Value in Kilohertz
        addItem(new ComboBoxItem("32 kHz", 32));
        addItem(new ComboBoxItem("100 kHz", 100));
        addItem(new ComboBoxItem("500 kHz", 500));
        addItem(new ComboBoxItem("1 MHz", 1000));
        addItem(new ComboBoxItem("2 MHz", 2000));
        addItem(new ComboBoxItem("4 MHz", 4000));
        addItem(new ComboBoxItem("8 MHz", 8000));
        addItem(new ComboBoxItem("12 MHz", 12000));
        addItem(new ComboBoxItem("16 MHz", 16000));
        addItem(new ComboBoxItem("20 MHz", 20000));


        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (pic != null) {
            setPICQuarzSpeed();
        }
    }

    public static class ComboBoxItem {
        private final String name;
        private final double speed;

        public ComboBoxItem(String name, double value) {
            this.name = name;
            this.speed = value;
        }

        public String getName() {
            return name;
        }

        public double getSpeed() {
            return speed;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    private void setPICQuarzSpeed() {
        if (pic == null) return;
        pic.setQuarzSpeed(((ComboBoxItem) Objects.requireNonNull(getSelectedItem())).getSpeed());
    }

    public void setPIC(PIC16F8X pic) {
        this.pic = pic;
        setSelectedIndex(5);//4Mhz
        setPICQuarzSpeed();
    }
}
