package org.example.entities;

import org.example.interfaces.Lamp;

public class Switch {
    private Lamp lamp;
    private boolean isOn;

    public Switch() {
    }

    public Switch(Lamp lamp, boolean isOn) {
        this.lamp = lamp;
        this.isOn = isOn;
        if (isOn) {
            lamp.on();
        }

    }

    public void changeLamp(Lamp lamp) {
        this.lamp.off();
        this.lamp = lamp;
        if (this.isOn) {
            lamp.on();
        }

    }

    public boolean isSwitchON() {
        return this.isOn;
    }

    public void switchState() {
        if (this.isOn) {
            this.isOn = false;
            this.lamp.off();
        } else {
            this.isOn = true;
            this.lamp.on();
        }

    }

    public String toString() {
        return "Switch{lamp=" + this.lamp + ", isOn=" + this.isOn + "}";
    }
}
