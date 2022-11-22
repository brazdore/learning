import templates.Coffee;
import templates.Tea;

public class Main {

    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {

        System.out.println(ANSI_PURPLE + "[COFFEE]");
        Coffee coffee = new Coffee();
        coffee.makeDrink();

        System.out.println(ANSI_PURPLE + "[TEA]");
        Tea tea = new Tea();
        tea.makeDrink();

        System.out.print(ANSI_RESET);
    }
}

