package com.brazdore.test.services;

import com.brazdore.configs.AppConfig;
import com.brazdore.entities.Customer;
import com.brazdore.exceptions.InvalidDataFormatException;
import com.brazdore.services.CustomerService;
import com.brazdore.services.strategies.DataServiceStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(AppConfig.class)
public class CustomerServiceTest {

    private static final String[] ARRAY_01 = {"002", "1234567891234", "Amanda Florença de Florençiana Esperança da Silva", "Entretenimento"};
    private static final Customer EXPECTED_CUSTOMER_01 = new Customer(ARRAY_01[1], ARRAY_01[2], ARRAY_01[3]);

    private static final String[] ARRAY_02 = {"002", "3245678865434", "Renato", "Estatal"};
    private static final Customer EXPECTED_CUSTOMER_02 = new Customer(ARRAY_02[1], ARRAY_02[2], ARRAY_02[3]);

    private static final String[] ARRAY_03 = {"002", "1234567891234", "Diego", "RH"};
    private static final Customer EXPECTED_CUSTOMER_03 = new Customer(ARRAY_03[1], ARRAY_03[2], ARRAY_03[3]);

    private static final String[] ARRAY_04 = {"002", "3245678865434", "Renato", "Rural"};
    private static final Customer EXPECTED_CUSTOMER_04 = new Customer(ARRAY_04[1], ARRAY_04[2], ARRAY_04[3]);

    private static final String[] ARRAY_05 = {"002", "2345675433444345", "EduardoPereira", "Rural"};
    private static final Customer EXPECTED_CUSTOMER_05 = new Customer(ARRAY_05[1], ARRAY_05[2], ARRAY_05[3]);

    private static final String[] INVALID_ARRAY = {"005", "12489402931", "Takanashi Kiara", "Entretenimento"};

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DataServiceStrategy DataServiceStrategy;


    @BeforeEach
    void init() {
        customerService.clear();
    }

    @Test
    void shouldSaveCustomer() {
        Assertions.assertTrue(customerService.save(ARRAY_01));
        Assertions.assertEquals(EXPECTED_CUSTOMER_01, customerService.getList().get(0));

        Assertions.assertTrue(customerService.save(ARRAY_02));
        Assertions.assertEquals(EXPECTED_CUSTOMER_02, customerService.getList().get(1));

        Assertions.assertTrue(customerService.save(ARRAY_03));
        Assertions.assertEquals(EXPECTED_CUSTOMER_03, customerService.getList().get(2));

        Assertions.assertTrue(customerService.save(ARRAY_04));
        Assertions.assertEquals(EXPECTED_CUSTOMER_04, customerService.getList().get(3));

        Assertions.assertTrue(customerService.save(ARRAY_05));
        Assertions.assertEquals(EXPECTED_CUSTOMER_05, customerService.getList().get(4));

    }

    @Test
    void shouldGetProperCustomerAmount() {
        Assertions.assertTrue(customerService.save(ARRAY_01));
        Assertions.assertTrue(customerService.save(ARRAY_02));
        Assertions.assertTrue(customerService.save(ARRAY_03));

        Assertions.assertEquals("3", customerService.getCustomerAmountString());
        customerService.clear();

        Assertions.assertTrue(customerService.save(ARRAY_01));
        Assertions.assertTrue(customerService.save(ARRAY_02));
        Assertions.assertTrue(customerService.save(ARRAY_03));
        Assertions.assertTrue(customerService.save(ARRAY_04));
        Assertions.assertTrue(customerService.save(ARRAY_05));

        Assertions.assertEquals("5", customerService.getCustomerAmountString());
    }

    @Test
    void strategyShouldSaveCustomer() {
        Assertions.assertEquals(0, customerService.getList().size());

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_01));
        Assertions.assertEquals(EXPECTED_CUSTOMER_01, customerService.getList().get(0));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_02));
        Assertions.assertEquals(EXPECTED_CUSTOMER_02, customerService.getList().get(1));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_03));
        Assertions.assertEquals(EXPECTED_CUSTOMER_03, customerService.getList().get(2));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_04));
        Assertions.assertEquals(EXPECTED_CUSTOMER_04, customerService.getList().get(3));

        Assertions.assertTrue(DataServiceStrategy.save(ARRAY_05));
        Assertions.assertEquals(EXPECTED_CUSTOMER_05, customerService.getList().get(4));

        Assertions.assertEquals(5, customerService.getList().size());
    }

    @Test
    void strategyShouldThrowAtInvalidID() {
        Assertions.assertThrows(InvalidDataFormatException.class, () ->
                DataServiceStrategy.save(INVALID_ARRAY), "[InvalidDataFormatException] Should throw when [DATA ID] is invalid.");
    }
}
