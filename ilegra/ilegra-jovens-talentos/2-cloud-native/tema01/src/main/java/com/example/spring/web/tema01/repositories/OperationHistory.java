package com.example.spring.web.tema01.repositories;

import com.example.spring.web.tema01.entities.Operation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OperationHistory {

    private final List<Operation> operationList = new ArrayList<>();

    public List<Operation> getOperationList() {
        return operationList;
    }

    public void addToHistory(Operation operation) {
        operationList.add(operation);
    }

    public void clearAll() {
        operationList.clear();
    }

    public void removeLast() {
        operationList.remove(operationList.size() - 1);
    }
}
