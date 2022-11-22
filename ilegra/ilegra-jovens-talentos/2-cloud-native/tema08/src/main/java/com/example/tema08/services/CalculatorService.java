package com.example.tema08.services;

import com.example.tema08.entities.Operation;
import com.example.tema08.repositories.CalculatorHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<Operation> getHistory() {
        return calculatorHistory.getHistory();
    }

    public void clear() {
        calculatorHistory.clear();
    }
}
