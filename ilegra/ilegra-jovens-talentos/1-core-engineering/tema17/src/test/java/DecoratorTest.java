import decorator.BasePizza;
import decorator.Pizza;
import decorator.nonvegan.BaconTopping;
import decorator.nonvegan.CheeseTopping;
import decorator.vegan.BroccoliTopping;
import decorator.vegan.CornTopping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DecoratorTest {

    private final BasePizza basePizza = new BasePizza();

    @Test
    public void shouldCreateProperPizza() {
        Pizza pizza = new BaconTopping(new CheeseTopping(new CheeseTopping(basePizza)));
        Assertions.assertEquals(pizza.getPrice(), 29.5);

        Pizza veganPizza = new CornTopping(new BroccoliTopping(new BroccoliTopping(new BroccoliTopping(new BroccoliTopping(basePizza)))));
        Assertions.assertEquals(veganPizza.getPrice(), 29.5);
    }
}
