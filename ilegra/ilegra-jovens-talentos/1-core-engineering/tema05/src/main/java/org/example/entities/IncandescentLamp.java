package org.example.entities;

import org.example.interfaces.Lamp;

public class IncandescentLamp implements Lamp {
    private boolean isLampOn;

    public IncandescentLamp() {
    }

    public boolean isLampOn() {
        return this.isLampOn;
    }

    public void on() {
        this.isLampOn = true;
    }

    public void off() {
        this.isLampOn = false;
    }

    public String toString() {
        return "IncandescentLamp{lampOn=" + this.isLampOn + "}";
    }
}
