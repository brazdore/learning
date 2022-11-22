package com.brazdore.services;

import com.brazdore.entities.Salesman;
import com.brazdore.exceptions.InvalidDataFormatException;
import com.brazdore.repositories.SalesmanRepository;
import com.brazdore.services.strategies.IDataService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service(value = "001")
public class SalesmanService implements IDataService {

    private final SalesmanRepository repository;

    public SalesmanService(SalesmanRepository repository) {
        this.repository = repository;
    }

    public List<Salesman> getList() {
        return repository.getList();
    }

    public Boolean save(String[] dataArray) {
        try {
            new BigDecimal(dataArray[3]);
        } catch (NumberFormatException e) {
            throw new InvalidDataFormatException(e.getMessage());
        }
        return repository.save(new Salesman(dataArray[1], dataArray[2], new BigDecimal(dataArray[3])));
    }

    public void clear() {
        repository.clear();
    }

    public String getSalesmanAmountString() {
        return String.valueOf(getList().size());
    }

}
