package decorator;

public abstract class ToppingDecorator implements Pizza {

    private final Pizza pizza;

    public ToppingDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public Double getPrice() {
        return pizza.getPrice();
    }
}
