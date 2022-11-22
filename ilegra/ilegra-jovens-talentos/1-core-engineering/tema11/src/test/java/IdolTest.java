import com.example.factories.IdolFactory;
import com.example.interfaces.Idol;
import exceptions.IdolFactoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdolTest {

    @Test
    public void shouldCreatePureIdol() {
        Idol pure = IdolFactory.getIdol("PURE");
        String pureSing = "PURE idol is singing. Angelic!";
        Assertions.assertEquals(pure.sing(), pureSing);
    }

    @Test
    public void shouldCreateCorruptedIdol() {
        Idol corrupted = IdolFactory.getIdol("CORRUPTED");
        String corruptedSing = "CORRUPTED idol is singing. Evil!";
        Assertions.assertEquals(corrupted.sing(), corruptedSing);
    }

    @Test
    public void shouldCreateHaachamaIdol() {
        Idol haachama = IdolFactory.getIdol("HAACHAMA");
        String haachamaSing = "HAACHAMA is singing. To the hills!";
        Assertions.assertEquals(haachama.sing(), haachamaSing);
    }

    @Test
    public void shouldThrowWhenInvalidInput() {
        Assertions.assertThrows(IdolFactoryException.class, () ->
                IdolFactory.getIdol("INVALID"), "Should throw when invalid String input.");
    }
}
