package command;

import entities.BankAccount;
import enums.OperationType;

public class WithdrawOperation implements BankOperation {

    private BankAccount bankAccount;
    private Double value;

    public WithdrawOperation() {
    }

    public WithdrawOperation(BankAccount bankAccount, Double value) {
        this.bankAccount = bankAccount;
        this.value = value;
    }

    @Override
    public void execute() {
        bankAccount.withdraw(this.value);
    }

    @Override
    public Double getValue() {
        return this.value * -1;
    }

    @Override
    public OperationType getType() {
        return OperationType.WITHDRAW;
    }

    @Override
    public Long getAccountId() {
        return bankAccount.getId();
    }

    @Override
    public String toString() {
        return "WithdrawOperation{" +
                "bankAccount=" + bankAccount +
                ", value=" + value +
                '}';
    }
}
