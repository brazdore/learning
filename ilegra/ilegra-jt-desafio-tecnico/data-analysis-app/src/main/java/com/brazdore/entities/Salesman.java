package com.brazdore.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Salesman {

    private String cpf;
    private String name;
    private BigDecimal salary;
    private BigDecimal revenue;

    public Salesman() {
    }

    public Salesman(String cpf, String name, BigDecimal salary) {
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salesman salesman = (Salesman) o;
        return cpf.equals(salesman.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Salesman{" +
                "cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", revenue=" + revenue +
                '}';
    }
}
