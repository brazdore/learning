package repositories;

import entities.Pet;
import interfaces.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PetRepository implements IRepository<Pet> {

    private final List<Pet> petList = new ArrayList<>();

    @Override
    public List<Pet> getAll() {
        return petList;
    }

    public void update(Pet pet) {
        int i = petList.indexOf(findById(pet.getId()).get());
        petList.set(i, pet);
    }

    @Override
    public Pet save(Pet var1) {
        petList.add(var1);
        return var1;
    }

    @Override
    public void saveList(List<Pet> var1) {
        petList.addAll(var1);
    }

    @Override
    public Optional<Pet> findById(long varID) {
        return petList.stream()
                .filter(x -> Objects.equals(varID, x.getId()))
                .findFirst();
    }

    public List<Pet> findByAge(int age) {
        return petList.stream()
                .filter(x -> Objects.equals(age, x.getAge()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(long varID) {
        return findById(varID).isPresent();
    }

    @Override
    public long count() {
        return petList.size();
    }

    @Override
    public void deleteById(long varID) {
        petList.removeIf(x -> Objects.equals(varID, x.getId()));
    }

    @Override
    public void deleteAll() {
        petList.clear();
    }
}
