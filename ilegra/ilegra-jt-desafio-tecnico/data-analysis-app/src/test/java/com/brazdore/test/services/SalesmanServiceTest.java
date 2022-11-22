package com.brazdore.test.services;

import com.brazdore.configs.AppConfig;
import com.brazdore.entities.Salesman;
import com.brazdore.exceptions.InvalidDataFormatException;
import com.brazdore.services.SalesmanService;
import com.brazdore.services.strategies.DataServiceStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;

@SpringJUnitConfig(AppConfig.class)
public class SalesmanServiceTest {

    private static final String[] ARRAY_01 = {"001", "1234567891234", "Amanda Florença de Florençiana Esperança da Silva", "50000"};
    private static final Salesman EXPECTED_SALESMAN_01 = new Salesman(ARRAY_01[1], ARRAY_01[2], new BigDecimal(ARRAY_01[3]));

    private static final String[] ARRAY_02 = {"001", "3245678865434", "Esperança", "40000.99"};
    private static final Salesman EXPECTED_SALESMAN_02 = new Salesman(ARRAY_02[1], ARRAY_02[2], new BigDecimal(ARRAY_02[3]));

    private static final String[] ARRAY_03 = {"001", "1234567891234", "Diego", "300.99"};
    private static final Salesman EXPECTED_SALESMAN_03 = new Salesman(ARRAY_03[1], ARRAY_03[2], new BigDecimal(ARRAY_03[3]));

    private static final String[] ARRAY_04 = {"001", "3245678865434", "Renato", "850.99"};
    private static final Salesman EXPECTED_SALESMAN_04 = new Salesman(ARRAY_04[1], ARRAY_04[2], new BigDecimal(ARRAY_04[3]));

    private static final String[] ARRAY_05 = {"001", "2345675433444345", "Eduardo Pereira de Çá", "15000"};
    private static final Salesman EXPECTED_SALESMAN_05 = new Salesman(ARRAY_05[1], ARRAY_05[2], new BigDecimal(ARRAY_05[3]));

    private static final String[] INVALID_ARRAY_01 = {"001", "2345675433444345", "Eduardo Pereira de Çá", "not a number"};

    private static final String[] INVALID_ARRAY_02 = {"005", "12489402931", "Takanashi Kiara", "Entretenimento"};

    @Autowired
    private SalesmanService salesmanService;

    @Autowired
    private DataServiceStrategy DataServiceStrategy;

    @BeforeEach
    void init() {
        salesmanService.clear();
    }

    @Test
    void shouldSaveSalesman() {
        Assertions.assertTrue(salesmanService.save(ARRAY_01));
        Assertions.assertEquals(EXPECTED_SALESMAN_01, salesmanService.getList().get(0));

        Assertions.assertTrue(salesmanService.save(ARRAY_02));
        Assertions.assertEquals(EXPECTED_SALESMAN_02, salesmanService.getList().get(1));

        Assertions.assertTrue(salesmanService.save(ARRAY_03));
        Assertions.assertEquals(EXPECTED_SALESMAN_03, salesmanService.getList().get(2));

        Assertions.assertTrue(salesmanService.save(ARRAY_04));
        Assertions.assertEquals(EXPECTED_SALESMAN_04, salesmanService.getList().get(3));

        Assertions.assertTrue(salesmanService.save(ARRAY_05));
        Assertions.assertEquals(EXPECTED_SALESMAN_05, salesmanService.getList().get(4));
    }

    @Test
    void shouldGetProperSalesmanAmount() {
        Assertions.assertTrue(salesmanService.save(ARRAY_01));
        Assertions.assertTrue(salesmanService.save(ARRAY_02));
        Assertions.assertTrue(salesmanService.save(ARRAY_03));

        Assertions.assertEquals("3", salesmanService.getSalesmanAmountString());
        salesmanService.clear();

        Assertions.assertTrue(salesmanService.save(ARRAY_01));
        Assertions.assertTrue(salesmanService.save(ARRAY_02));
        Assertions.assertTrue(salesmanService.save(ARRAY_03));
        Assertions.assertTrue(salesmanService.save(ARRAY_04));
        Assertions.assertTrue(salesmanService.save(ARRAY_05));

        Assertions.assertEquals("5", salesmanService.getSalesmanAmountString());
    }

    @Test
    void shouldThrowAtInvalidSalary() {
        Assertions.assertThrows(InvalidDataFormatException.class, () ->
                salesmanService.save(INVALID_ARRAY_01), "[InvalidDataFormatException] Should throw when [SALARY] is not a valid number");
    }

    @Test
    void strategyShouldSaveSalesman() {
        Assertions.assertEquals(0, salesmanService.getList().size());

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_01));
        Assertions.assertEquals(EXPECTED_SALESMAN_01, salesmanService.getList().get(0));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_02));
        Assertions.assertEquals(EXPECTED_SALESMAN_02, salesmanService.getList().get(1));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_03));
        Assertions.assertEquals(EXPECTED_SALESMAN_03, salesmanService.getList().get(2));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_04));
        Assertions.assertEquals(EXPECTED_SALESMAN_04, salesmanService.getList().get(3));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_05));
        Assertions.assertEquals(EXPECTED_SALESMAN_05, salesmanService.getList().get(4));

        Assertions.assertEquals(5, salesmanService.getList().size());
    }

    @Test
    void strategyShouldThrowAtInvalidSalary() {
        Assertions.assertThrows(InvalidDataFormatException.class, () ->
                DataServiceStrategy.save(INVALID_ARRAY_01), "[InvalidDataFormatException] Should throw when [SALARY] is not a valid number");
    }

    @Test
    void strategyShouldThrowAtInvalidID() {
        Assertions.assertThrows(InvalidDataFormatException.class, () ->
                DataServiceStrategy.save(INVALID_ARRAY_02), "[InvalidDataFormatException] Should throw when [DATA ID] is invalid.");
    }
}
