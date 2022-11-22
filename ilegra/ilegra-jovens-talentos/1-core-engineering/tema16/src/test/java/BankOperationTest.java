import command.DepositOperation;
import command.WithdrawOperation;
import entities.BankAccount;
import exceptions.BankAccountBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.BankService;

import java.util.Optional;

public class BankOperationTest {

    private final BankService bankService = new BankService();
    private BankAccount kiaraAccount;
    private WithdrawOperation withdrawOperation;
    private DepositOperation depositOperation;

    @BeforeEach
    public void init() {
        bankService.clear();
        kiaraAccount = new BankAccount(1L, "Kiara", 1000.0);
    }

    @Test
    public void shouldDeposit() {
        Assertions.assertEquals(bankService.listSize(), 0);

        depositOperation = new DepositOperation(kiaraAccount, 500.0);
        bankService.addOrder(depositOperation);
        depositOperation = new DepositOperation(kiaraAccount, 204.0);
        bankService.addOrder(depositOperation);

        Assertions.assertEquals(bankService.listSize(), 2);

        bankService.executeOrders();

        Assertions.assertEquals(bankService.listSize(), 0);

        Assertions.assertEquals(kiaraAccount.getBalance(), 1704.0);
    }

    @Test
    public void shouldWithdraw() {
        Assertions.assertEquals(bankService.listSize(), 0);

        withdrawOperation = new WithdrawOperation(kiaraAccount, 500.0);
        bankService.addOrder(withdrawOperation);
        withdrawOperation = new WithdrawOperation(kiaraAccount, 499.0);
        bankService.addOrder(withdrawOperation);

        Assertions.assertEquals(bankService.listSize(), 2);

        bankService.executeOrders();

        Assertions.assertEquals(bankService.listSize(), 0);

        Assertions.assertEquals(kiaraAccount.getBalance(), 1.0);
    }

    @Test
    public void shouldSortOperationHistoryByID() {

        withdrawOperation = new WithdrawOperation(kiaraAccount, 100.0);
        bankService.addOrder(withdrawOperation);
        withdrawOperation = new WithdrawOperation(kiaraAccount, 249.0);
        bankService.addOrder(withdrawOperation);

        BankAccount guraAccount = new BankAccount(2L, "Gura", 1000.0);

        depositOperation = new DepositOperation(guraAccount, 300.0);
        bankService.addOrder(depositOperation);
        depositOperation = new DepositOperation(guraAccount, 588.0);
        bankService.addOrder(depositOperation);

        Double kiaraBalance = bankService
                .sortOperationsByID()
                .get(1L)
                .stream()
                .reduce(Double::sum)
                .get();

        Double guraBalance = bankService
                .sortOperationsByID()
                .get(2L)
                .stream()
                .reduce(Double::sum)
                .get();

        Assertions.assertEquals(kiaraBalance, -349.0);
        Assertions.assertEquals(guraBalance, 888.0);
    }

    @Test
    public void shouldGetOperationBalanceByID() {

        withdrawOperation = new WithdrawOperation(kiaraAccount, 100.0);
        bankService.addOrder(withdrawOperation);
        withdrawOperation = new WithdrawOperation(kiaraAccount, 249.0);
        bankService.addOrder(withdrawOperation);

        BankAccount guraAccount = new BankAccount(2L, "Gura", 1000.0);

        depositOperation = new DepositOperation(guraAccount, 300.0);
        bankService.addOrder(depositOperation);
        depositOperation = new DepositOperation(guraAccount, 588.0);
        bankService.addOrder(depositOperation);

        Assertions.assertEquals(bankService.getOperationBalanceByID(1L).get(), -349.0);
        Assertions.assertEquals(bankService.getOperationBalanceByID(2L).get(), 888.0);
    }

    @Test
    public void shouldReturnOptionalEmptyWhenInvalidID() {
        Assertions.assertEquals(bankService.getOperationBalanceByID(399L), Optional.empty());
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsToWithdrawFrom() {

        withdrawOperation = new WithdrawOperation(kiaraAccount, 1999.0);
        bankService.addOrder(withdrawOperation);

        Assertions.assertThrows(BankAccountBalanceException.class, bankService::executeOrders,
                "Should throw when not enough funds to withdraw.");
    }
}
