import entities.Converter;
import exceptions.ConverterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {

    Converter converter = new Converter();

    @Test
    public void shouldConvertFromStringIntToStringRom() {
        assertEquals(converter.decToRoman("20"), "XX");
        assertEquals(converter.decToRoman("4"), "IV");
        assertEquals(converter.decToRoman("9"), "IX");
        assertEquals(converter.decToRoman("3"), "III");
        assertEquals(converter.decToRoman("19"), "XIX");
    }

    @Test
    public void shouldConvertFromStringRomToStringInt() {
        assertEquals(converter.romanToDec("XX"), "20");
        assertEquals(converter.romanToDec("IV"), "4");
        assertEquals(converter.romanToDec("IX"), "9");
        assertEquals(converter.romanToDec("III"), "3");
        assertEquals(converter.romanToDec("iii"), "3");
        assertEquals(converter.romanToDec("XIX"), "19");
    }

    @Test
    public void shouldThrowConverterException() {
        Assertions.assertThrows(ConverterException.class, () ->
                this.converter.decToRoman("21"), "Should throw at bigger than 20 - DEC");

        Assertions.assertThrows(ConverterException.class, () ->
                this.converter.decToRoman("0"), "Should throw at 0 or less - DEC");

        Assertions.assertThrows(ConverterException.class, () ->
                this.converter.romanToDec("XXI"), "Should throw at bigger than 20 - ROM");

        Assertions.assertThrows(ConverterException.class, () ->
                this.converter.romanToDec("VVVVV"), "Should throw at bigger than 20 - ROM");
    }
}
