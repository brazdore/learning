package templates;

public class Coffee extends HotDrink {

    public static final String ANSI_YELLOW = "\u001B[33m";

    @Override
    void gatherIngredients() {
        System.out.println(ANSI_YELLOW + "You gathered some coffee beans and hot water.");
    }

    @Override
    void prepareIngredients() {
        System.out.println(ANSI_YELLOW + "You ground the coffee beans.");
    }

    @Override
    void mixIngredients() {
        System.out.println(ANSI_YELLOW + "You filtered your ground coffee to perfection.");
    }

    @Override
    void pourIntoRecipient() {
        System.out.println(ANSI_YELLOW + "Hmmm... a nice hot just-brewed cup of coffee was made.");
    }
}
