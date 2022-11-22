package command;

import entities.BankAccount;
import enums.OperationType;

public class DepositOperation implements BankOperation {

    private BankAccount bankAccount;
    private Double value;

    public DepositOperation() {
    }

    public DepositOperation(BankAccount bankAccount, Double value) {
        this.bankAccount = bankAccount;
        this.value = value;
    }

    @Override
    public void execute() {
        bankAccount.deposit(this.value);
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public OperationType getType() {
        return OperationType.DEPOSIT;
    }

    @Override
    public Long getAccountId() {
        return bankAccount.getId();
    }

    @Override
    public String toString() {
        return "DepositOperation{" +
                "bankAccount=" + bankAccount +
                ", value=" + value +
                '}';
    }
}
