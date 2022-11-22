package com.example.tema03.web.services;

import com.example.tema03.web.entities.Operation;
import com.example.tema03.web.enums.OperationType;
import com.example.tema03.web.exceptions.CalculatorDivisionException;
import com.example.tema03.web.exceptions.CalculatorInputException;
import com.example.tema03.web.repositories.OperationHistory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CalculatorService {

    private final OperationHistory operationHistory = new OperationHistory();

    public Double addition(String x, String y) {
        tryToAddOperationToHistory(OperationType.ADDITION, x, y);
        return Double.parseDouble(x) + Double.parseDouble(y);
    }

    public Double subtraction(String x, String y) {
        tryToAddOperationToHistory(OperationType.SUBTRACTION, x, y);
        return Double.parseDouble(x) - Double.parseDouble(y);
    }

    public Double multiplication(String x, String y) {
        tryToAddOperationToHistory(OperationType.MULTIPLICATION, x, y);
        return Double.parseDouble(x) * Double.parseDouble(y);
    }

    public Double division(String x, String y) {
        tryToAddOperationToHistory(OperationType.DIVISION, x, y);

        if (Objects.equals(Math.abs(Double.parseDouble(y)), 0D)) {
            operationHistory.removeLast();
            throw new CalculatorDivisionException("Cannot divide by zero.");
        }
        return Double.parseDouble(x) / Double.parseDouble(y);
    }

    public Double exponentiation(String x, String y) {
        tryToAddOperationToHistory(OperationType.EXPONENTIATION, x, y);
        return Math.pow(Double.parseDouble(x), Double.parseDouble(y));
    }

    public List<Operation> getHistory() {
        return operationHistory.getOperationList();
    }

    public void clearAll() {
        operationHistory.clearAll();
    }

    private void tryToAddOperationToHistory(OperationType operationType, String x, String y) {
        try {
            operationHistory.addToHistory(new Operation(operationType, Arrays.asList(Double.valueOf(x), Double.valueOf(y))));
        } catch (RuntimeException e) {
            throw new CalculatorInputException("At least one input is not valid: x=(" + x + ") & y=(" + y + ")");
        }
    }
}
