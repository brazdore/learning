package services;

import command.BankOperation;
import enums.OperationType;

import java.util.*;
import java.util.stream.Collectors;

public class BankService {

    private static final List<BankOperation> operations = new ArrayList<>();

    public void addOrder(BankOperation operation) {
        operations.add(operation);
    }

    public void executeOrders() {
        for (BankOperation operation : operations) {
            operation.execute();
        }
        operations.clear();
    }

    public Optional<Double> getOperationBalanceByID(Long id) {
        if (!containsID(id)) {
            return Optional.empty();
        }

        final Double[] withdraw = {0.0};
        final Double[] deposit = {0.0};

        operations.stream()
                .filter(x -> Objects.equals(x.getAccountId(), id))
                .filter(x -> Objects.equals(x.getType(), OperationType.DEPOSIT))
                .forEach(x -> withdraw[0] += x.getValue());

        operations.stream()
                .filter(x -> Objects.equals(x.getAccountId(), id))
                .filter(x -> Objects.equals(x.getType(), OperationType.WITHDRAW))
                .forEach(x -> deposit[0] += x.getValue());

        return Optional.of(deposit[0] + withdraw[0]);
    }

    public Map<Long, List<Double>> sortOperationsByID() {
        return operations.stream()
                .collect(Collectors.groupingBy(BankOperation::getAccountId, Collectors.mapping(BankOperation::getValue, Collectors.toList())));
    }

    public int listSize() {
        return operations.size();
    }

    public void clear() {
        operations.clear();
    }

    private boolean containsID(Long id) {
        return !Objects.equals(operations.stream()
                .filter(x -> Objects.equals(x.getAccountId(), id))
                .count(), 0L);
    }
}
