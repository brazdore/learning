package command;

import enums.OperationType;

public interface BankOperation {
    void execute();

    Double getValue();

    OperationType getType();

    Long getAccountId();
}
