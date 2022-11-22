package entities;

import enums.SpeciesType;

import java.util.Objects;

public class Pet {

    private Long id;
    private String name;
    private SpeciesType species;
    private String race;
    private Integer age;
    private Integer serviceCount;

    public Pet() {
    }

    public Pet(Long id, String name, SpeciesType species, Integer age) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.serviceCount = 0;
    }

    public Long getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public void increaseServiceCount() {
        serviceCount++;
    }

    public Integer getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
    }

    public void setRace(String race) {
        this.race = race;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id.equals(pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + '\'' +
                ", species=" + species +
                ", race='" + race + '\'' +
                ", age=" + age +
                ", serviceCount=" + serviceCount +
                '}';
    }
}
