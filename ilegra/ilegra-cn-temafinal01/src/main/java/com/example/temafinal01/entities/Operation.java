package com.example.temafinal01.entities;

import com.example.temafinal01.enums.OperationType;

public class Operation {

    private OperationType type;
    private Double x;
    private Double y;

    public Operation(OperationType type, String x, String y) {
        this.type = type;
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }

    public OperationType getType() {
        return type;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
