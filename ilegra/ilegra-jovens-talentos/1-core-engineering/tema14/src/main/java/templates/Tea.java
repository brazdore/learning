package templates;

public class Tea extends HotDrink {

    public static final String ANSI_GREEN = "\u001B[32m";

    @Override
    void gatherIngredients() {
        System.out.println(ANSI_GREEN + "You gathered some leaves and hot water.");
    }

    @Override
    void prepareIngredients() {
        System.out.println(ANSI_GREEN + "You ground the leaves.");
    }

    @Override
    void mixIngredients() {
        System.out.println(ANSI_GREEN + "You add the ground leaves to the hot water.");
    }

    @Override
    void pourIntoRecipient() {
        System.out.println(ANSI_GREEN + "Very well made tea indeed. Fancy yourself a bri'ish yet?");
    }
}
