package com.example.spring.command.tema01.services;

import com.example.spring.command.tema01.interfaces.Operation;
import com.example.spring.command.tema01.repositories.CalculatorHistory;

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
