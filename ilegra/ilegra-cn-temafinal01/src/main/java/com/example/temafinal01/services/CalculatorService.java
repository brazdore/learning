package com.example.temafinal01.services;

import com.example.temafinal01.entities.Operation;
import com.example.temafinal01.enums.OperationType;
import com.example.temafinal01.exceptions.CalculatorDivisionException;
import com.example.temafinal01.exceptions.CalculatorInputException;
import com.example.temafinal01.repositories.CalculatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {

    private final CalculatorRepository operationHistory;

    public CalculatorService(CalculatorRepository operationHistory) {
        this.operationHistory = operationHistory;
    }

    public Double addition(String x, String y) {
        isValidElseThrow(x, y);
        operationHistory.add(new Operation(OperationType.ADDITION, x, y));
        return Double.parseDouble(x) + Double.parseDouble(y);
    }

    public Double subtraction(String x, String y) {
        isValidElseThrow(x, y);
        operationHistory.add(new Operation(OperationType.SUBTRACTION, x, y));
        return Double.parseDouble(x) - Double.parseDouble(y);
    }

    public Double multiplication(String x, String y) {
        isValidElseThrow(x, y);
        operationHistory.add(new Operation(OperationType.MULTIPLICATION, x, y));
        return Double.parseDouble(x) * Double.parseDouble(y);
    }

    public Double division(String x, String y) {
        isValidElseThrow(x, y);

        if (Double.parseDouble(y) == 0D) {
            throw new CalculatorDivisionException("Cannot divide by zero.");
        }

        operationHistory.add(new Operation(OperationType.DIVISION, x, y));
        return Double.parseDouble(x) / Double.parseDouble(y);
    }

    public Double exponentiation(String x, String y) {
        isValidElseThrow(x, y);
        operationHistory.add(new Operation(OperationType.EXPONENTIATION, x, y));
        return Math.pow(Double.parseDouble(x), Double.parseDouble(y));
    }

    public List<Operation> getHistory() {
        return operationHistory.getHistory();
    }

    public void clear() {
        operationHistory.clear();
    }

    private void isValidElseThrow(String x, String y) {
        try {
            Double.parseDouble(x);
            Double.parseDouble(y);
        } catch (NullPointerException | NumberFormatException e) {
            throw new CalculatorInputException("At least one input is not valid: x=(" + x + ") & y=(" + y + ")");
        }
    }
}
