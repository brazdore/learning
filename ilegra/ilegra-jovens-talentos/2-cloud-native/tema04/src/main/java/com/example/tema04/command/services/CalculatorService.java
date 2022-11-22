package com.example.tema04.command.services;

import com.example.tema04.command.interfaces.Operation;
import com.example.tema04.command.repositories.CalculatorHistory;

public class CalculatorService {

    private final CalculatorHistory calculatorHistory;

    public CalculatorService(CalculatorHistory calculatorHistory) {
        this.calculatorHistory = calculatorHistory;
    }

    public boolean areBothValid(String x, String y) {
        try {
            Double.parseDouble(x);
            Double.parseDouble(y);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Double execute(Operation operation) {
        this.calculatorHistory.addToHistory(operation);
        return operation.execute();
    }

    public CalculatorHistory getCalculatorHistory() {
        return calculatorHistory;
    }
}
