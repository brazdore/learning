import com.google.inject.Guice;
import com.google.inject.Injector;
import entities.Pet;
import enums.BathType;
import enums.HaircutType;
import enums.SpeciesType;
import exceptions.PetException;
import modules.PetModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.OperationService;

public class OperationServiceTest {

    private final Injector injector = Guice.createInjector(new PetModule());
    private final OperationService operationService = injector.getInstance(OperationService.class);

    private Pet korone;

    @BeforeEach
    void init() {
        korone = new Pet(1L, "Korone", SpeciesType.DOG, 20);
        operationService.getPetService().deleteAll();
        operationService.getTupleService().deleteAll();
    }

    @Test
    public void shouldPerformBathWaterWithPerfume() {
        Assertions.assertEquals(0, korone.getServiceCount());
        Pet koroneSaved = operationService.getPetService().save(korone);
        operationService.bath(1L, BathType.WATER_BATH_WITH_PERFUME);
        Assertions.assertEquals(koroneSaved, korone);
        Assertions.assertEquals(1, operationService.getTupleService().count());
        Assertions.assertEquals(korone.getId(), operationService.getTupleService().getList().get(0).getPetID());
    }

    @Test
    public void shouldPerformBathDryWithPerfume() {
        Assertions.assertEquals(0, korone.getServiceCount());
        operationService.getPetService().save(korone);
        operationService.bath(1L, BathType.DRY_BATH_WITH_PERFUME);
        Assertions.assertEquals(1, operationService.getTupleService().count());
        Assertions.assertEquals(korone.getId(), operationService.getTupleService().getList().get(0).getPetID());
    }

    @Test
    public void shouldPerformBathWaterWithoutPerfume() {
        Assertions.assertEquals(0, korone.getServiceCount());
        operationService.getPetService().save(korone);
        operationService.bath(1L, BathType.WATER_BATH_WITHOUT_PERFUME);
        Assertions.assertEquals(1, operationService.getTupleService().count());
        Assertions.assertEquals(korone.getId(), operationService.getTupleService().getList().get(0).getPetID());
    }

    @Test
    public void shouldPerformBathDryWithoutPerfume() {
        Assertions.assertEquals(0, korone.getServiceCount());
        operationService.getPetService().save(korone);
        operationService.bath(1L, BathType.DRY_BATH_WITHOUT_PERFUME);
        Assertions.assertEquals(1, operationService.getTupleService().count());
        Assertions.assertEquals(korone.getId(), operationService.getTupleService().getList().get(0).getPetID());
    }

    @Test
    public void shouldPerformHaircutLong() {
        Assertions.assertEquals(0, korone.getServiceCount());
        operationService.getPetService().save(korone);
        operationService.haircut(1L, HaircutType.HAIRCUT_LONG);
        Assertions.assertEquals(1, operationService.getTupleService().count());
        Assertions.assertEquals(korone.getId(), operationService.getTupleService().getList().get(0).getPetID());
    }

    @Test
    public void shouldPerformHaircutShort() {
        Assertions.assertEquals(0, korone.getServiceCount());
        operationService.getPetService().save(korone);
        operationService.haircut(1L, HaircutType.HAIRCUT_SHORT);
        Assertions.assertEquals(1, operationService.getTupleService().count());
        Assertions.assertEquals(korone.getId(), operationService.getTupleService().getList().get(0).getPetID());
    }

    @Test
    public void shouldThrowWhenTryingToBathInvalidIDPet() {
        Assertions.assertThrows(PetException.class, () ->
                operationService.bath(99L, BathType.DRY_BATH_WITH_PERFUME), "Should throw when invalid [ID] from [PET].");
    }

    @Test
    public void shouldThrowWhenTryingToHaircutInvalidIDPet() {
        Assertions.assertThrows(PetException.class, () ->
                operationService.haircut(99L, HaircutType.HAIRCUT_LONG), "Should throw when invalid [ID] from [PET].");
    }

    @Test
    void shouldSaveAllServices() {
        Assertions.assertEquals(0, korone.getServiceCount());
        operationService.getPetService().save(korone);
        operationService.bath(1L, BathType.DRY_BATH_WITH_PERFUME);
        operationService.haircut(1L, HaircutType.HAIRCUT_SHORT);
        operationService.bath(1L, BathType.WATER_BATH_WITHOUT_PERFUME);
        operationService.haircut(1L, HaircutType.HAIRCUT_SHORT);
        Assertions.assertEquals(4, operationService.getTupleService().count());
        Assertions.assertEquals(korone.getId(), operationService.getTupleService().getList().get(0).getPetID());
    }
}
