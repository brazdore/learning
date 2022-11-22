package services;

import entities.HistoryTuple;
import entities.Pet;
import enums.BathType;
import enums.HaircutType;
import exceptions.PetException;
import interfaces.IServices;

import javax.inject.Inject;

public class OperationService {

    private final PetService petService;
    private final TupleService tupleService;

    @Inject
    public OperationService(PetService petService, TupleService tupleService) {
        this.petService = petService;
        this.tupleService = tupleService;
    }

    public void bath(long petID, BathType bathType) {
        throwWhenInvalidPetID(petID);
        getAndUpdatePet(petID);
        buildAndSaveTuple(petID, bathType);
    }

    public void haircut(long petID, HaircutType haircutType) {
        throwWhenInvalidPetID(petID);
        getAndUpdatePet(petID);
        buildAndSaveTuple(petID, haircutType);
    }

    private void buildAndSaveTuple(long petID, IServices serviceType) {
        HistoryTuple tuple = new HistoryTuple(tupleService.count() + 1, petID, serviceType);
        tupleService.save(tuple);
    }

    private void throwWhenInvalidPetID(long petID) {
        if (!petService.existsById(petID)) {
            throw new PetException("No pets with [ID]: " + petID + " were found.");
        }
    }

    private void getAndUpdatePet(long petID) {
        Pet pet = petService.findById(petID).get();
        pet.increaseServiceCount();
        petService.update(pet);
    }

    public PetService getPetService() {
        return petService;
    }

    public TupleService getTupleService() {
        return tupleService;
    }
}
