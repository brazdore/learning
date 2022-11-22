package entities;

import exceptions.BankAccountBalanceException;

import java.util.Objects;

public class BankAccount {

    private long id;
    private String ownerName;
    private Double balance;

    public BankAccount() {
    }

    public BankAccount(long id, String ownerName, Double credit) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = credit;
    }

    public long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Double getBalance() {
        return balance;
    }

    public void deposit(Double value) {
        this.balance += value;
    }

    public void withdraw(Double value) {
        if (value > this.balance) {
            throw new BankAccountBalanceException("You do not have enough funds.");
        }
        this.balance -= value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", ownerName='" + ownerName + '\'' +
                ", credit=" + balance +
                '}';
    }
}
