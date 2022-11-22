package com.brazdore.services;

import com.brazdore.exceptions.FileWriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class FileWriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterService.class);

    private final CustomerService customerService;
    private final SalesmanService salesmanService;
    private final SaleService saleService;

    public FileWriterService(CustomerService customerService, SalesmanService salesmanService, SaleService saleService) {
        this.customerService = customerService;
        this.salesmanService = salesmanService;
        this.saleService = saleService;
    }

    public Boolean writeFile(String finalOutputPath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(finalOutputPath))) {
            bw.write(customerService.getCustomerAmountString());
            bw.newLine();
            bw.write(salesmanService.getSalesmanAmountString());
            bw.newLine();
            bw.write("" + saleService.getMostExpensiveSaleID().getKey() + ":" + saleService.getMostExpensiveSaleID().getValue());
            bw.newLine();
            bw.write("" + saleService.getWorstSalesmanName().getKey() + ":" + saleService.getWorstSalesmanName().getValue());

            customerService.clear();
            salesmanService.clear();
            saleService.clear();
        } catch (IOException e) {
            throw new FileWriterException(e.getMessage());
        }
        LOGGER.info("File: Written");
        LOGGER.info("Output Path: {}", finalOutputPath);
        return true;
    }
}
