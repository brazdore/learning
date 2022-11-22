package com.example.factories;

import com.example.entities.Corrupted;
import com.example.entities.Haachama;
import com.example.entities.Pure;
import com.example.interfaces.Idol;
import exceptions.IdolFactoryException;

public class IdolFactory {

    public static Idol getIdol(String idolType) {
        if (idolType.equalsIgnoreCase("PURE")) {
            return new Pure();
        } else if (idolType.equalsIgnoreCase("CORRUPTED")) {
            return new Corrupted();
        } else if (idolType.equalsIgnoreCase("HAACHAMA")) {
            return new Haachama();
        } else
            throw new IdolFactoryException("Sorry partner, invalid input. You can pick a) PURE, b) CORRUPTED or c) HAACHAMA.");
    }
}
