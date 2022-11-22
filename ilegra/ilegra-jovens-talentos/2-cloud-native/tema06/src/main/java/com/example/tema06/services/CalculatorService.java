package com.example.tema06.services;

import com.example.tema06.interfaces.Operation;
import com.example.tema06.repositories.CalculatorHistory;

public class CalculatorService {

    private final CalculatorHistory calculatorHistory;

    public CalculatorService(CalculatorHistory calculatorHistory) {
        this.calculatorHistory = calculatorHistory;
    }

    public Double execute(Operation operation) {
        this.calculatorHistory.addToHistory(operation);
        return operation.execute();
    }

    public CalculatorHistory getCalculatorHistory() {
        return calculatorHistory;
    }
}
