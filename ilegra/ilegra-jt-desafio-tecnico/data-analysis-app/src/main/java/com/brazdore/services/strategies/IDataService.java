package com.brazdore.services.strategies;

import org.springframework.stereotype.Service;

@Service
public interface IDataService {
    Boolean save(String[] dataArray);
}