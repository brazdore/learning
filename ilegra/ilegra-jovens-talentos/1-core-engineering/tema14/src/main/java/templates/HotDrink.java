package templates;

public abstract class HotDrink {
    abstract void gatherIngredients();

    abstract void prepareIngredients();

    abstract void mixIngredients();

    abstract void pourIntoRecipient();

    public final void makeDrink() {
        gatherIngredients();

        prepareIngredients();

        mixIngredients();

        pourIntoRecipient();
    }
}
