package com.example.temafinal01.repositories;


import com.example.temafinal01.entities.Operation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CalculatorRepository {

    private final List<Operation> history = new ArrayList<>();

    public List<Operation> getHistory() {
        return history;
    }

    public void add(Operation operation) {
        history.add(operation);
    }

    public void clear() {
        history.clear();
    }
}
