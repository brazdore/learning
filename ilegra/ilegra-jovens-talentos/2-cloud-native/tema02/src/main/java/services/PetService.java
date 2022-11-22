package services;

import entities.Pet;
import exceptions.PetException;
import repositories.PetRepository;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PetService {

    private final PetRepository petRepository;

    @Inject
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> getList() {
        return petRepository.getAll();
    }

    public void update(Pet pet) {
        if (!existsById(pet.getId())) {
            throw new PetException("No pets with [ID]: " + pet.getId() + " were found.");
        }
        petRepository.update(pet);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public void saveList(List<Pet> petList) {
        petRepository.saveList(petList);
    }

    public Optional<Pet> findById(long petID) {
        return petRepository.findById(petID);
    }

    public List<Pet> findByAge(int age) {
        return petRepository.findByAge(age);
    }

    public boolean existsById(long petID) {
        return petRepository.existsById(petID);
    }

    public long count() {
        return petRepository.count();
    }

    public void deleteById(long id) {
        petRepository.deleteById(id);
    }

    public void deleteAll() {
        petRepository.deleteAll();
    }

    public List<Pet> listTopServiceCountPets(int n) {
        return petRepository.getAll()
                .stream()
                .sorted(Comparator.comparingInt(Pet::getServiceCount).reversed().thenComparingLong(Pet::getId))
                .limit(n)
                .collect(Collectors.toList());
    }
}
