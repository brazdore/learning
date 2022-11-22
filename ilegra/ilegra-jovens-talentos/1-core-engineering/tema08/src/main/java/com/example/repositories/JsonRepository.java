package com.example.repositories;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface JsonRepository<T> {

    List<T> getAll() throws IOException;

    T save(T var1) throws IOException;

    void overwrite(List<T> var1) throws IOException;

    Optional<T> findById(long varID) throws IOException;

    boolean existsById(long varID) throws IOException;

    long count() throws IOException;

    void deleteById(long varID) throws IOException;

    void deleteAll() throws IOException;
}