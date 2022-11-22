package com.brazdore.configs;

import com.brazdore.repositories.CustomerRepository;
import com.brazdore.repositories.SaleRepository;
import com.brazdore.repositories.SalesmanRepository;
import com.brazdore.services.*;
import com.brazdore.services.strategies.DataServiceStrategy;
import com.brazdore.services.strategies.IDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    private static final String INPUT_FOLDER = "in/";
    private static final String OUTPUT_FOLDER = "out/";

    @Value("${BASE_DIR}")
    private String BASE_DIR;

    @Value("${SIZE_LIMIT}")
    private String SIZE_LIMIT;

    @Bean("sizeLimit")
    public String getSizeLimit() {
        return SIZE_LIMIT;
    }

    @Bean("basePath")
    public String getBasePath() {
        return BASE_DIR;
    }

    @Bean("inputPath")
    public String getInputPath() {
        return getBasePath() + INPUT_FOLDER;
    }

    @Bean("outputPath")
    public String getOutputPath() {
        return getBasePath() + OUTPUT_FOLDER;
    }

    @Bean("saleRepository")
    public SaleRepository getSaleRepository() {
        return new SaleRepository();
    }

    @Bean("saleService")
    public SaleService getSaleService() {
        return new SaleService(getSaleRepository());
    }

    @Bean("customerRepository")
    public CustomerRepository getCustomerRepository() {
        return new CustomerRepository();
    }

    @Bean("customerService")
    public CustomerService getCustomerService() {
        return new CustomerService(getCustomerRepository());
    }

    @Bean("salesmanRepository")
    public SalesmanRepository getSalesmanRepository() {
        return new SalesmanRepository();
    }

    @Bean("salesmanService")
    public SalesmanService getSalesmanService() {
        return new SalesmanService(getSalesmanRepository());
    }

    @Bean("strategyMap")
    public Map<String, IDataService> getStrategyMap() {
        Map<String, IDataService> strategyMap = new HashMap<>();
        strategyMap.put("001", getSalesmanService());
        strategyMap.put("002", getCustomerService());
        strategyMap.put("003", getSaleService());
        return strategyMap;
    }

    @Bean("dataStrategy")
    public DataServiceStrategy getDataStrategy() {
        return new DataServiceStrategy(getStrategyMap());
    }

    @Bean("fileReader")
    public FileReaderService getFileReaderService() {
        return new FileReaderService(getDataStrategy());
    }

    @Bean("fileWriter")
    public FileWriterService getFileWriterService() {
        return new FileWriterService(getCustomerService(), getSalesmanService(), getSaleService());
    }

    @Bean("fileService")
    public FileService getFileService() {
        return new FileService(getFileReaderService(), getFileWriterService());
    }
}
