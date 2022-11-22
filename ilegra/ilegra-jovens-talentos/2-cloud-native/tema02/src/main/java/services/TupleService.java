package services;

import entities.HistoryTuple;
import repositories.TupleRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class TupleService {

    private final TupleRepository tupleRepository;

    @Inject
    public TupleService(TupleRepository tupleRepository) {
        this.tupleRepository = tupleRepository;
    }

    public List<HistoryTuple> getList() {
        return tupleRepository.getAll();
    }

    public HistoryTuple save(HistoryTuple tuple) {
        return tupleRepository.save(tuple);
    }

    public void saveList(List<HistoryTuple> tupleList) {
        tupleRepository.saveList(tupleList);
    }

    public Optional<HistoryTuple> findById(long tupleID) {
        return tupleRepository.findById(tupleID);
    }

    public boolean existsById(long tupleID) {
        return tupleRepository.existsById(tupleID);
    }

    public long count() {
        return tupleRepository.count();
    }

    public void deleteById(long id) {
        tupleRepository.deleteById(id);
    }

    public void deleteAll() {
        tupleRepository.deleteAll();
    }
}
