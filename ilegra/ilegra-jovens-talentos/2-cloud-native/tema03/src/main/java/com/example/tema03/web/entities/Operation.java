package com.example.tema03.web.entities;

import com.example.tema03.web.enums.OperationType;

import java.util.ArrayList;
import java.util.List;

public class Operation {

    private OperationType type;
    private List<Double> numbers = new ArrayList<>();

    public Operation() {
    }

    public Operation(OperationType type, List<Double> numbers) {
        this.type = type;
        this.numbers = numbers;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public List<Double> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Double> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "{type=" + type +
                ", numbers=" + numbers +
                '}';
    }
}
