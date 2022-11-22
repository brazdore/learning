package com.example.entities;

import com.example.interfaces.Idol;

public class Corrupted implements Idol {

    @Override
    public String sing() {
        return "CORRUPTED idol is singing. Evil!";
    }
}
