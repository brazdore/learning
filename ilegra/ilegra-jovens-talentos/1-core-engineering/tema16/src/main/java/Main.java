import command.DepositOperation;
import command.WithdrawOperation;
import entities.BankAccount;
import services.BankService;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {

        BankAccount kiaraAccount = new BankAccount(1L, "Kiara", 500.0);
        BankAccount guraAccount = new BankAccount(2L, "Gura", 300.0);

        DepositOperation kiaraDeposit1 = new DepositOperation(kiaraAccount, 50.0);
        WithdrawOperation kiaraWithdraw1 = new WithdrawOperation(kiaraAccount, 150.0);
        WithdrawOperation kiaraWithdraw2 = new WithdrawOperation(kiaraAccount, 250.0);
        WithdrawOperation guraWithdraw1 = new WithdrawOperation(guraAccount, 250.0);

        BankService bankService = new BankService();

        bankService.addOrder(kiaraDeposit1);
        bankService.addOrder(kiaraWithdraw1);
        bankService.addOrder(kiaraWithdraw2);
        bankService.addOrder(guraWithdraw1);

        System.out.println(ANSI_PURPLE + bankService.sortOperationsByID());
        System.out.println(ANSI_RED + bankService.getOperationBalanceByID(399L));

        bankService.executeOrders();

        System.out.println(ANSI_YELLOW + kiaraAccount);
        System.out.print(ANSI_RESET);
    }
}
