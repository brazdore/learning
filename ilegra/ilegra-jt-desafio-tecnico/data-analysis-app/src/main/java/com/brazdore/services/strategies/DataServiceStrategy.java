package com.brazdore.services.strategies;

import com.brazdore.exceptions.InvalidDataFormatException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class DataServiceStrategy {

    private final Map<String, IDataService> strategyMap;

    public DataServiceStrategy(Map<String, IDataService> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public Boolean save(String[] dataArray) {
        if (Objects.isNull(strategyMap.get(dataArray[0]))) {
            throw new InvalidDataFormatException(dataArray[0]);
        } else return strategyMap.get(dataArray[0]).save(dataArray);
    }
}