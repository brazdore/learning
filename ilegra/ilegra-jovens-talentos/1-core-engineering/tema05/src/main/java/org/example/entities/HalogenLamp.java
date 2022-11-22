package org.example.entities;

import org.example.interfaces.Lamp;

public class HalogenLamp implements Lamp {
    private boolean isLampOn;

    public HalogenLamp() {
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
        return "HalogenLamp{lampOn=" + this.isLampOn + "}";
    }
}
