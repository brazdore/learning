package com.brazdore.test.services;

import com.brazdore.configs.AppConfig;
import com.brazdore.entities.Item;
import com.brazdore.entities.Sale;
import com.brazdore.exceptions.InvalidDataFormatException;
import com.brazdore.services.SaleService;
import com.brazdore.services.strategies.DataServiceStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.List;

@SpringJUnitConfig(AppConfig.class)
public class SaleServiceTest {

    private static final String[] ARRAY_01 = {"003", "10", "[1-10-100,2-30-2.50,3-40-3.10]", "Diego"};
    private static final String[] ARRAY_02 = {"003", "08", "[1-34-10,2-33-1.50,3-40-0.10]", "Renato"};
    private static final String[] INVALID_ARRAY_01 = {"003", "08", "[invalid item list]", "Renato"};
    private static final String[] INVALID_ARRAY_02 = {"005", "12489402931", "Takanashi Kiara", "Entretenimento"};

    private static final List<Item> EXPECTED_LIST_01 = List.of(new Item("1", 10L, new BigDecimal("100")),
            new Item("2", 30L, new BigDecimal("2.50")),
            new Item("3", 40L, new BigDecimal("3.10")));

    private static final List<Item> EXPECTED_LIST_02 = List.of(new Item("1", 34L, new BigDecimal("10")),
            new Item("2", 33L, new BigDecimal("1.50")),
            new Item("3", 40L, new BigDecimal("0.10")));

    private static final Sale EXPECTED_SALE_01 = new Sale(ARRAY_01[1], EXPECTED_LIST_01, ARRAY_01[3]);

    private static final Sale EXPECTED_SALE_02 = new Sale(ARRAY_02[1], EXPECTED_LIST_02, ARRAY_02[3]);


    @Autowired
    private SaleService saleService;

    @Autowired
    private DataServiceStrategy DataServiceStrategy;

    @BeforeEach
    void init() {
        saleService.clear();
    }

    @Test
    void shouldSaveSale() {
        Assertions.assertTrue(saleService.save(ARRAY_01));
        Assertions.assertTrue(saleService.save(ARRAY_02));

        Assertions.assertEquals(2, saleService.getList().size());
        Assertions.assertEquals(EXPECTED_SALE_01, saleService.getList().get(0));
        Assertions.assertEquals(EXPECTED_SALE_02, saleService.getList().get(1));
    }

    @Test
    void shouldGetWorstSalesmanName() {
        saleService.save(ARRAY_01);
        saleService.save(ARRAY_02);

        Assertions.assertEquals(new AbstractMap.SimpleEntry<>(new BigDecimal("393.50"), List.of("Renato")),
                saleService.getWorstSalesmanName());
    }

    @Test
    void shouldGetMostExpensiveSaleID() {
        saleService.save(ARRAY_01);
        saleService.save(ARRAY_02);

        Assertions.assertEquals(new AbstractMap.SimpleEntry<>(new BigDecimal("1199.00"), List.of("10")),
                saleService.getMostExpensiveSaleID());
    }

    @Test
    void strategyShouldSaveSale() {
        Assertions.assertEquals(0, saleService.getList().size());

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_01));
        Assertions.assertEquals(EXPECTED_SALE_01, saleService.getList().get(0));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_02));
        Assertions.assertEquals(EXPECTED_SALE_02, saleService.getList().get(1));

        Assertions.assertEquals(2, saleService.getList().size());
    }

    @Test
    void strategyShouldThrowWhenInvalidItemList() {
        Assertions.assertThrows(InvalidDataFormatException.class, () ->
                DataServiceStrategy.save(INVALID_ARRAY_01), "[InvalidDataFormatException] Should throw when [ITEM LIST] is invalid.");
    }

    @Test
    void strategyShouldThrowAtInvalidID() {
        Assertions.assertThrows(InvalidDataFormatException.class, () ->
                DataServiceStrategy.save(INVALID_ARRAY_02), "[InvalidDataFormatException] Should throw when [DATA ID] is invalid.");
    }
}
