import com.google.inject.Guice;
import com.google.inject.Injector;
import entities.Pet;
import enums.SpeciesType;
import exceptions.PetException;
import modules.PetModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.PetService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PetServiceTest {

    private final Injector injector = Guice.createInjector(new PetModule());
    private final PetService petService = injector.getInstance(PetService.class);

    private Pet korone;
    private Pet kiara;

    @BeforeEach
    void init() {
        korone = new Pet(1L, "Inugami Korone", SpeciesType.DOG, 20);
        kiara = new Pet(2L, "Takanashi Kiara", SpeciesType.BIRD, 9999);
        petService.deleteAll();
    }

    @Test
    public void shouldSavePet() {
        petService.save(korone);
        Assertions.assertEquals(petService.count(), 1L);
        Assertions.assertEquals(petService.getList().get(0), korone);
    }

    @Test
    public void shouldSavePetList() {
        petService.saveList(List.of(korone, kiara));
        Assertions.assertEquals(petService.count(), 2L);
        Assertions.assertEquals(petService.getList().get(0), korone);
        Assertions.assertEquals(petService.getList().get(1), kiara);
    }

    @Test
    public void shouldFindPetByAge() {
        petService.save(korone);
        Assertions.assertEquals(petService.findByAge(korone.getAge()), List.of(korone));
    }

    @Test
    public void shouldFindPetsByAge() {
        petService.saveList(List.of(korone, kiara));
        Assertions.assertEquals(petService.findByAge(korone.getAge()), List.of(korone));
        Assertions.assertEquals(petService.findByAge(kiara.getAge()), List.of(kiara));
    }

    @Test
    void shouldFindPetByID() {
        petService.save(korone);
        Assertions.assertEquals(petService.findById(1L).get(), korone);
    }

    @Test
    void shouldNotFindPetByID() {
        petService.saveList(List.of(kiara, korone));
        Assertions.assertTrue(petService.findById(15L).isEmpty());
    }

    @Test
    void shouldAssertTrueAtExistsByID() {
        petService.save(korone);
        Assertions.assertTrue(petService.existsById(1L));
    }

    @Test
    void shouldAssertFalseAtExistsByID() {
        Assertions.assertFalse(petService.existsById(1L));
    }

    @Test
    public void shouldDeletePetByID() {
        petService.save(korone);
        Assertions.assertEquals(petService.getList().get(0), korone);
        petService.deleteById(1L);
        Assertions.assertEquals(petService.count(), 0L);
    }

    @Test
    public void shouldThrowWhenTryingToUpdateNotSavedPet() {
        petService.save(korone);
        Assertions.assertThrows(PetException.class, () ->
                petService.update(kiara), "Should throw exception when updating not-saved pet.");
    }

    @Test
    public void shouldListNPetsWithMostCount() {
        Pet ina = new Pet(3L, "Ina", SpeciesType.FISH, 50);
        Pet botan = new Pet(4L, "Botan", SpeciesType.CAT, 32);
        Pet capybara = new Pet(5L, "Coconut Doggy", SpeciesType.CAPYBARA, 394);
        Pet coco = new Pet(6L, "Coco", SpeciesType.LIZARD, 203045);
        Pet bae = new Pet(7L, "Chaos", SpeciesType.RAT, 203);
        Pet bao = new Pet(8L, "Bao", SpeciesType.FISH, 20405);
        Pet pekora = new Pet(9L, "Pekora", SpeciesType.RABBIT, 22);
        Pet ham = new Pet(10L, "Ham the Hamster", SpeciesType.HAMSTER, 2);

        petService.saveList(List.of(korone, kiara, ina, botan, capybara, coco, bae, bao, pekora, ham));

        korone.setServiceCount(13); // 1
        botan.setServiceCount(12); // 2
        capybara.setServiceCount(9); // 3
        coco.setServiceCount(9); // 4
        bae.setServiceCount(8); // 5
        bao.setServiceCount(5); // 6
        ina.setServiceCount(4); // 7
        kiara.setServiceCount(2); // 8
        pekora.setServiceCount(1); // 9

        List<Pet> sortedList = petService.getList().stream()
                .sorted(Comparator.comparingInt(Pet::getServiceCount).reversed().thenComparingLong(Pet::getId))
                .limit(10)
                .collect(Collectors.toList());

        Assertions.assertEquals(petService.listTopServiceCountPets(10), List.of(korone, botan, capybara, coco,
                bae, bao, ina, kiara, pekora, ham));

        Assertions.assertEquals(petService.listTopServiceCountPets(10), sortedList);
        Assertions.assertEquals(petService.listTopServiceCountPets(5), List.of(korone, botan, capybara, coco, bae));
        Assertions.assertEquals(petService.listTopServiceCountPets(1), List.of(korone));
    }
}
