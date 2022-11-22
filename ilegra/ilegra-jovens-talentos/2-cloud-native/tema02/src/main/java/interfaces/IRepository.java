package interfaces;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {

    List<T> getAll();

    T save(T var1);

    void saveList(List<T> var1);

    Optional<T> findById(long varID);

    boolean existsById(long varID);

    long count();

    void deleteById(long varID);

    void deleteAll();
}
