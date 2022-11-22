import decorator.BasePizza;
import decorator.Pizza;
import decorator.nonvegan.BaconTopping;
import decorator.nonvegan.CheeseTopping;
import decorator.vegan.BroccoliTopping;
import decorator.vegan.CornTopping;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {

        BasePizza basePizza = new BasePizza();
        Pizza finalPizza = new BaconTopping(new CheeseTopping(new CheeseTopping(basePizza)));

        BasePizza anotherBasePizza = new BasePizza();
        Pizza veganPizza = new CornTopping(new BroccoliTopping(new BroccoliTopping(new BroccoliTopping(new BroccoliTopping(anotherBasePizza)))));

        System.out.println(ANSI_RED + "2x Cheese + 1x Bacon: $" + finalPizza.getPrice());
        System.out.println(ANSI_GREEN + "4x Cheese + 1x Corn: $" + veganPizza.getPrice());
        System.out.print(ANSI_RESET);
    }
}
