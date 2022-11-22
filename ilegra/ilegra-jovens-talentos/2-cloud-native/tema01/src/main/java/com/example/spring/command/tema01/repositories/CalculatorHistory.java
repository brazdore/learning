package com.example.spring.command.tema01.repositories;

import com.example.spring.command.tema01.interfaces.Operation;

import java.util.ArrayList;
import java.util.List;

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
