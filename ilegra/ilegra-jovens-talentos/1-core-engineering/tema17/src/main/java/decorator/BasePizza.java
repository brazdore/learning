package decorator;

public class BasePizza implements Pizza {
    @Override
    public Double getPrice() {
        return 20D;
    }
}
