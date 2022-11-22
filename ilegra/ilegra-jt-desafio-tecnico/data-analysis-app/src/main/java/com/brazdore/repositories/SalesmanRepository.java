package com.brazdore.repositories;

import com.brazdore.entities.Salesman;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesmanRepository {

    private final List<Salesman> salesmanList = new ArrayList<>();

    public SalesmanRepository() {
    }

    public Boolean save(Salesman salesman) {
        return salesmanList.add(salesman);
    }

    public List<Salesman> getList() {
        return salesmanList;
    }

    public void clear() {
        salesmanList.clear();
    }
}
