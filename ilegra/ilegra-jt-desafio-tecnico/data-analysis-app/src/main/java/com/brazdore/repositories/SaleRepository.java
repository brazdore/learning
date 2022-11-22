package com.brazdore.repositories;

import com.brazdore.entities.Sale;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SaleRepository {

    private final List<Sale> saleList = new ArrayList<>();

    public SaleRepository() {
    }

    public Boolean save(Sale sale) {
        return saleList.add(sale);
    }

    public List<Sale> getList() {
        return saleList;
    }

    public void clear() {
        saleList.clear();
    }
}
