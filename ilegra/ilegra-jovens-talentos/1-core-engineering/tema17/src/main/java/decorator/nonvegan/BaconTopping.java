package decorator.nonvegan;

import decorator.Pizza;
import decorator.ToppingDecorator;

public class BaconTopping extends ToppingDecorator {

    private static final Double BACON_FLAVOUR = 3.5;

    public BaconTopping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public Double getPrice() {
        return super.getPrice() + BACON_FLAVOUR;
    }
}