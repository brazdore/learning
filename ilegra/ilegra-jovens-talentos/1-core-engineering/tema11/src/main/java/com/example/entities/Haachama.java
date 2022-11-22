package com.example.entities;

import com.example.interfaces.Idol;

public class Haachama implements Idol {

    @Override
    public String sing() {
        return "HAACHAMA is singing. To the hills!";
    }
}
