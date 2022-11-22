package com.example.tema08.repositories;

import com.example.tema08.entities.Operation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalculatorHistory {

    private final List<Operation> history = new ArrayList<>();

    public CalculatorHistory() {
    }

    public List<Operation> getHistory() {
        return history;
    }

    public void clear() {
        history.clear();
    }

    public void addToHistory(Operation operation) {
        history.add(operation);
    }
}
