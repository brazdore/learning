package decorator.nonvegan;

import decorator.Pizza;
import decorator.ToppingDecorator;

public class CheeseTopping extends ToppingDecorator {

    private static final Double CHEESE_PRICE = 3D;

    public CheeseTopping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public Double getPrice() {
        return super.getPrice() + CHEESE_PRICE;
    }
}
