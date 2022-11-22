package com.brazdore.utils;

import com.brazdore.exceptions.InvalidDataFormatException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public abstract class DeserializationService {

    private final static String SPLIT_CHAR = "ç";
    private final static int START_NAME_INDEX_CUSTOMER_OR_SALESMAN = 2;
    private final static int START_NAME_INDEX_SALE = 3;
    private final static int VALUE_TO_SUBTRACT_CUSTOMER_OR_SALESMAN = 1;
    private final static int VALUE_TO_SUBTRACT_SALE = 0;
    private final static int EXPECTED_LENGTH = 4;
    private final static List<String> EXPECTED_IDS = List.of(" 001", " 002", " 003");

    public static List<String> getEntriesFromLine(String line) {
        final String[] replacedString = {line};
        EXPECTED_IDS.forEach(x -> replacedString[0] = replacedString[0].replace(x, x.replace(" ", "!")));
        replacedString[0] = replacedString[0].replace(System.lineSeparator(), "!");
        return List.of(replacedString[0].split("!"));
    }

    public static String[] getArrayFromEntry(String entry) {
        var split = entry.split(SPLIT_CHAR);

        if (split.length < EXPECTED_LENGTH) {
            throw new InvalidDataFormatException(Arrays.toString(split));
        }
        var startNameIndex = isCustomerOrSalesman(split[0]) ? START_NAME_INDEX_CUSTOMER_OR_SALESMAN : START_NAME_INDEX_SALE;
        String[] dataArray = new String[4];

        dataArray[0] = split[0];
        dataArray[1] = split[1];

        if (isCustomerOrSalesman(split[0])) {
            dataArray[2] = fixNameWithSplitChar(split, startNameIndex);
            dataArray[3] = split[split.length - 1];
        } else {
            dataArray[2] = split[2];
            dataArray[3] = fixNameWithSplitChar(split, startNameIndex);
        }
        return dataArray;
    }

    private static String fixNameWithSplitChar(String[] split, int startNameIndex) {
        if (split.length > EXPECTED_LENGTH) {
            var valueToSubract = isCustomerOrSalesman(split[0]) ? VALUE_TO_SUBTRACT_CUSTOMER_OR_SALESMAN : VALUE_TO_SUBTRACT_SALE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(split[startNameIndex]);

            for (int i = startNameIndex + 1; i < split.length - valueToSubract; i++) {
                stringBuilder.append(SPLIT_CHAR).append(split[i]);
            }
            return stringBuilder.toString();
        } else return split[startNameIndex];
    }

    private static boolean isCustomerOrSalesman(String id) {
        return id.equals("001") || id.equals("002");
    }
}
