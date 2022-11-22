package com.brazdore.test.utils;

import com.brazdore.exceptions.InvalidDataFormatException;
import com.brazdore.utils.DeserializationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DeserializationServiceTest {

    private static final String LINE_01 =
            "001ç1234567891234çAmanda Florençaç50000 " +
                    "001ç3245678865434çRenatoç40000.99";

    private static final String LINE_02 =
            "001ç1234567891234çDiegoç50000 " +
                    "001ç3245678865434çRenatoç40000.99 " +
                    "002ç2345675434544345çJose da SilvaçRural " +
                    "002ç2345675433444345çEduardoPereiraçRural " +
                    "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çFlorençiana de Florença";

    private static final List<String> LINE_01_LIST =
            List.of("001ç1234567891234çAmanda Florençaç50000",
                    "001ç3245678865434çRenatoç40000.99");

    private static final List<String> LINE_02_LIST =
            List.of("001ç1234567891234çDiegoç50000",
                    "001ç3245678865434çRenatoç40000.99",
                    "002ç2345675434544345çJose da SilvaçRural",
                    "002ç2345675433444345çEduardoPereiraçRural",
                    "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çFlorençiana de Florença");

    private static final String INVALID_ENTRY = "not a valid data entry";

    @Test
    void shouldGetEntriesFromLine() {
        Assertions.assertEquals(LINE_01_LIST, DeserializationService.getEntriesFromLine(LINE_01));
        Assertions.assertEquals(LINE_02_LIST, DeserializationService.getEntriesFromLine(LINE_02));
    }

    @Test
    void shouldGetDataArrayFromEntry() {
        var list01Array01 = DeserializationService.getArrayFromEntry(LINE_01_LIST.get(0));
        String[] expectedArray01 = {"001", "1234567891234", "Amanda Florença", "50000"};
        Assertions.assertEquals(Arrays.toString(expectedArray01), Arrays.toString(list01Array01));

        var list01Array02 = DeserializationService.getArrayFromEntry(LINE_01_LIST.get(1));
        String[] expectedArray02 = {"001", "3245678865434", "Renato", "40000.99"};
        Assertions.assertEquals(Arrays.toString(expectedArray02), Arrays.toString(list01Array02));

        var list02Array01 = DeserializationService.getArrayFromEntry(LINE_02_LIST.get(0));
        String[] expectedArray03 = {"001", "1234567891234", "Diego", "50000"};
        Assertions.assertEquals(Arrays.toString(expectedArray03), Arrays.toString(list02Array01));

        var list02Array02 = DeserializationService.getArrayFromEntry(LINE_02_LIST.get(1));
        String[] expectedArray04 = {"001", "3245678865434", "Renato", "40000.99"};
        Assertions.assertEquals(Arrays.toString(expectedArray04), Arrays.toString(list02Array02));

        var list02Array03 = DeserializationService.getArrayFromEntry(LINE_02_LIST.get(2));
        String[] expectedArray05 = {"002", "2345675434544345", "Jose da Silva", "Rural"};
        Assertions.assertEquals(Arrays.toString(expectedArray05), Arrays.toString(list02Array03));

        var list02Array04 = DeserializationService.getArrayFromEntry(LINE_02_LIST.get(3));
        String[] expectedArray06 = {"002", "2345675433444345", "EduardoPereira", "Rural"};
        Assertions.assertEquals(Arrays.toString(expectedArray06), Arrays.toString(list02Array04));

        var list02Array05 = DeserializationService.getArrayFromEntry(LINE_02_LIST.get(4));
        String[] expectedArray07 = {"003", "10", "[1-10-100,2-30-2.50,3-40-3.10]", "Florençiana de Florença"};
        Assertions.assertEquals(Arrays.toString(expectedArray07), Arrays.toString(list02Array05));
    }

    @Test
    void shouldThrowAtInvalidLine() {
        Assertions.assertThrows(InvalidDataFormatException.class, () ->
                DeserializationService.getArrayFromEntry(INVALID_ENTRY), "[InvalidDataFormatException] Should throw at invalid entry.");
    }
}
