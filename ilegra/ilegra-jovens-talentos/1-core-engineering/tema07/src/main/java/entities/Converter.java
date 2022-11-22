package entities;

import exceptions.ConverterException;

public class Converter {

    private static final int MAX_VALUE = 20;

    public String decToRoman(String number) {
        if (isValidDecimal(Integer.parseInt(number))) {
            return String.join("", doLoop(Integer.parseInt(number)))
                    .replace("IIIII", "V")
                    .replace("IIII", "IV")
                    .replace("VV", "X")
                    .replace("VIV", "IX")
                    .replace("XXXXX", "L")
                    .replace("XXXX", "XL")
                    .replace("LL", "C")
                    .replace("LXL", "XC")
                    .replace("CCCCC", "D")
                    .replace("CCCC", "CD")
                    .replace("DD", "M")
                    .replace("DCD", "CM");
        } else throw new ConverterException("Numbers allowed: 1 to 20.");
    }

    public String romanToDec(String string) {
        String s = String.valueOf(String.join(string, string)
                .replaceAll("IV", doLoop(4))
                .replaceAll("IX", doLoop(9))
                .replaceAll("XL", doLoop(40))
                .replaceAll("XC", doLoop(90))
                .replaceAll("CD", doLoop(400))
                .replaceAll("CM", doLoop(900))
                .replaceAll("V", doLoop(5))
                .replaceAll("X", doLoop(10))
                .replaceAll("L", doLoop(50))
                .replaceAll("C", doLoop(100))
                .replaceAll("D", doLoop(500))
                .replaceAll("M", doLoop(1000)).length());
        if (Integer.parseInt(s) <= MAX_VALUE) {
            return s;
        } else throw new ConverterException("Numbers allowed: I to XX.");
    }

    private String doLoop(int number) {
        return "I".repeat(Math.max(0, number));
    }

    private boolean isValidDecimal(int number) {
        return number > 0 && number <= MAX_VALUE;
    }
}
