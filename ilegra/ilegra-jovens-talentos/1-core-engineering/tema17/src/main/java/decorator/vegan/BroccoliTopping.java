package decorator.vegan;

import decorator.Pizza;
import decorator.ToppingDecorator;

public class BroccoliTopping extends ToppingDecorator {

    private static final Double BROCCOLI_PRICE = 2D;

    public BroccoliTopping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public Double getPrice() {
        return super.getPrice() + BROCCOLI_PRICE;
    }
}
