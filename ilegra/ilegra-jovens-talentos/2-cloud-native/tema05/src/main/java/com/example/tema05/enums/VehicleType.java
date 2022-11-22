package com.example.tema05.enums;

import java.util.HashMap;
import java.util.Map;

public enum VehicleType {
    BUS(1.59),
    MOTORCYCLE(1.0),
    BIKE(0.45),
    BEETLE(2.11),
    TRUCK(3.95),
    EXTRA_TRUCK_AXLE(1.0);

    public final Double tollPrice;

    VehicleType(Double tollPrice) {
        this.tollPrice = tollPrice;
    }

    public static Map<VehicleType, Double> getTollMap() {
        Map<VehicleType, Double> map = new HashMap<>();

        for (VehicleType vehicleType : values()) {
            map.put(vehicleType, vehicleType.tollPrice);
        }
        return map;
    }

    public static boolean isValid(String vehicleString) {
        try {
            VehicleType.valueOf(vehicleString);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isTruck(String vehicleString) {
        return VehicleType.valueOf(vehicleString).equals(VehicleType.TRUCK);
    }

    public Double getTollPrice() {
        return this.tollPrice;
    }
}
