package repositories;

import entities.HistoryTuple;
import interfaces.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TupleRepository implements IRepository<HistoryTuple> {

    private final List<HistoryTuple> serviceList = new ArrayList<>();

    @Override
    public List<HistoryTuple> getAll() {
        return serviceList;
    }

    @Override
    public HistoryTuple save(HistoryTuple var1) {
        serviceList.add(var1);
        return var1;
    }

    @Override
    public void saveList(List<HistoryTuple> var1) {
        serviceList.addAll(var1);
    }

    @Override
    public Optional<HistoryTuple> findById(long varID) {
        return serviceList.stream()
                .filter(x -> Objects.equals(x.getServiceID(), varID))
                .findFirst();
    }

    @Override
    public boolean existsById(long varID) {
        return findById(varID).isPresent();
    }

    @Override
    public long count() {
        return serviceList.size();
    }

    @Override
    public void deleteById(long varID) {
        serviceList.removeIf(x -> Objects.equals(x.getServiceID(), varID));
    }

    @Override
    public void deleteAll() {
        serviceList.clear();
    }
}
