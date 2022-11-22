package com.example.tema05.repositories;

import com.example.tema05.enums.VehicleType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TollRepository {

    private final Map<VehicleType, Integer> tollMap = new HashMap<>();

    public Map<VehicleType, Integer> getTollMap() {
        return tollMap;
    }

    public void save(VehicleType vehicleType) {
        int i = 1;
        if (tollMap.containsKey(vehicleType)) {
            i += tollMap.get(vehicleType);
            tollMap.put(vehicleType, i);
        } else {
            tollMap.put(vehicleType, i);
        }
    }

    public void clear() {
        tollMap.clear();
    }
}
