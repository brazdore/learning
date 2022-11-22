package com.brazdore.services;

import com.brazdore.entities.Customer;
import com.brazdore.repositories.CustomerRepository;
import com.brazdore.services.strategies.IDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "002")
public class CustomerService implements IDataService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }

    public List<Customer> getList() {
        return repository.getList();
    }

    public Boolean save(String[] dataArray) {
        return repository.save(new Customer(dataArray[1], dataArray[2], dataArray[3]));
    }

    public void clear() {
        repository.clear();
    }

    public String getCustomerAmountString() {
        return String.valueOf(getList().size());
    }
}
