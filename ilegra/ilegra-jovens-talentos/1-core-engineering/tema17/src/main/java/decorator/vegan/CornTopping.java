package decorator.vegan;

import decorator.Pizza;
import decorator.ToppingDecorator;

public class CornTopping extends ToppingDecorator {

    private static final Double CORN_PRICE = 1.5;

    public CornTopping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public Double getPrice() {
        return super.getPrice() + CORN_PRICE;
    }
}
