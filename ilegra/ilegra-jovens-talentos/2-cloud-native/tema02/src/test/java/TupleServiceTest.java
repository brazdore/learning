import com.google.inject.Guice;
import com.google.inject.Injector;
import entities.HistoryTuple;
import entities.Pet;
import enums.BathType;
import enums.HaircutType;
import enums.SpeciesType;
import modules.PetModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.TupleService;

import java.util.List;

public class TupleServiceTest {

    private final Injector injector = Guice.createInjector(new PetModule());
    private final TupleService tupleService = injector.getInstance(TupleService.class);

    private final Pet korone = new Pet(1L, "Inugami Korone", SpeciesType.DOG, 20);
    private final Pet kiara = new Pet(2L, "Takanashi Kiara", SpeciesType.BIRD, 9999);

    private HistoryTuple koroneTuple;
    private HistoryTuple kiaraTuple;

    @BeforeEach
    void init() {
        koroneTuple = new HistoryTuple(1L, korone.getId(), BathType.DRY_BATH_WITH_PERFUME);
        kiaraTuple = new HistoryTuple(2L, kiara.getId(), HaircutType.HAIRCUT_SHORT);
        tupleService.deleteAll();
    }

    @Test
    public void shouldSaveTuple() {
        HistoryTuple koroneTupleSaved = tupleService.save(koroneTuple);
        Assertions.assertEquals(tupleService.count(), 1L);
        Assertions.assertEquals(tupleService.getList().get(0), koroneTuple);
        Assertions.assertEquals(koroneTupleSaved, koroneTuple);
    }

    @Test
    public void shouldSaveTupleList() {
        tupleService.saveList(List.of(koroneTuple, kiaraTuple));
        Assertions.assertEquals(tupleService.count(), 2L);
        Assertions.assertEquals(tupleService.getList().get(0), koroneTuple);
        Assertions.assertEquals(tupleService.getList().get(1), kiaraTuple);
    }

    @Test
    void shouldFindTupleByID() {
        tupleService.save(koroneTuple);
        Assertions.assertEquals(tupleService.findById(1L).get(), koroneTuple);
    }

    @Test
    void shouldNotFindTupleByID() {
        tupleService.saveList(List.of(kiaraTuple, koroneTuple));
        Assertions.assertTrue(tupleService.findById(15L).isEmpty());
    }

    @Test
    void shouldAssertTrueAtExistsByID() {
        tupleService.save(koroneTuple);
        Assertions.assertTrue(tupleService.existsById(1L));
    }

    @Test
    void shouldAssertFalseAtExistsByID() {
        Assertions.assertFalse(tupleService.existsById(1L));
    }

    @Test
    public void shouldDeleteTupleByID() {
        tupleService.save(koroneTuple);
        Assertions.assertEquals(tupleService.getList().get(0), koroneTuple);
        tupleService.deleteById(1L);
        Assertions.assertEquals(tupleService.count(), 0L);
    }
}
