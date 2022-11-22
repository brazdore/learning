package com.example.tema05;

import com.example.tema05.enums.VehicleType;
import com.example.tema05.exceptions.InvalidAxleException;
import com.example.tema05.exceptions.InvalidPaymentException;
import com.example.tema05.exceptions.InvalidVehicleException;
import com.example.tema05.exceptions.TollPaymentException;
import com.example.tema05.repositories.TollRepository;
import com.example.tema05.services.TollService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TollServiceTest {

    private final TollService tollService = new TollService(new TollRepository());

    @BeforeEach
    void init() {
        tollService.clear();
    }

    @Test
    public void shouldPerformPaymentBUS() {
        var vehicleString = "BUS";
        var change = BigDecimal.valueOf(10).subtract(BigDecimal.valueOf(VehicleType.valueOf(vehicleString).getTollPrice()));
        Assertions.assertEquals(tollService.standardPay(vehicleString, "10"), change);
        Assertions.assertEquals(tollService.getTollMap().size(), 1);
    }

    @Test
    public void shouldPerformPaymentMOTORCYCLE() {
        var vehicleString = "MOTORCYCLE";
        var change = BigDecimal.valueOf(10).subtract(BigDecimal.valueOf(VehicleType.valueOf(vehicleString).getTollPrice()));
        Assertions.assertEquals(tollService.standardPay(vehicleString, "10"), change);
        Assertions.assertEquals(tollService.getTollMap().size(), 1);
    }

    @Test
    public void shouldPerformPaymentBIKE() {
        var vehicleString = "BIKE";
        var change = BigDecimal.valueOf(10).subtract(BigDecimal.valueOf(VehicleType.valueOf(vehicleString).getTollPrice()));
        Assertions.assertEquals(tollService.standardPay(vehicleString, "10"), change);
        Assertions.assertEquals(tollService.getTollMap().size(), 1);
    }

    @Test
    public void shouldPerformPaymentBEETLE() {
        var vehicleString = "BEETLE";
        var change = BigDecimal.valueOf(10).subtract(BigDecimal.valueOf(VehicleType.valueOf(vehicleString).getTollPrice()));
        Assertions.assertEquals(tollService.standardPay(vehicleString, "10"), change);
        Assertions.assertEquals(tollService.getTollMap().size(), 1);
    }

    @Test
    public void shouldPerformPaymentNormalTRUCK() {
        var vehicleString = "TRUCK";
        var change = BigDecimal.valueOf(10).subtract(BigDecimal.valueOf(VehicleType.valueOf(vehicleString).getTollPrice()));
        Assertions.assertEquals(tollService.standardPay(vehicleString, "10"), change);
        Assertions.assertEquals(tollService.getTollMap().size(), 1);
    }

    @Test
    public void shouldPerformPaymentAxleTRUCK() {
        var vehicleString = "TRUCK";
        var change = BigDecimal.valueOf(10).subtract(BigDecimal.valueOf(VehicleType.valueOf(vehicleString).getTollPrice()));
        Assertions.assertEquals(tollService.axlePay(vehicleString, "2", "10"), change.subtract(BigDecimal.valueOf(2)));
        Assertions.assertEquals(tollService.getTollMap().size(), 1);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsBUS() {
        var vehicleString = "BUS";
        Assertions.assertThrows(TollPaymentException.class, () ->
                tollService.standardPay(vehicleString, "1"), "[BUS] Should throw when pay is not enough.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsMOTORCYCLE() {
        var vehicleString = "MOTORCYCLE";
        Assertions.assertThrows(TollPaymentException.class, () ->
                tollService.standardPay(vehicleString, "0.5"), "[MOTORCYCLE] Should throw when pay is not enough.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsBIKE() {
        var vehicleString = "BIKE";
        Assertions.assertThrows(TollPaymentException.class, () ->
                tollService.standardPay(vehicleString, "0.3"), "[BIKE] Should throw when pay is not enough.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsBEETLE() {
        var vehicleString = "BEETLE";
        Assertions.assertThrows(TollPaymentException.class, () ->
                tollService.standardPay(vehicleString, "2.0"), "[BEETLE] Should throw when pay is not enough.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsNormalTRUCK() {
        var vehicleString = "TRUCK";
        Assertions.assertThrows(TollPaymentException.class, () ->
                tollService.standardPay(vehicleString, "3.0"), "[NORMAL TRUCK] Should throw when pay is not enough.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsAxleTRUCK() {
        var vehicleString = "TRUCK";
        Assertions.assertThrows(TollPaymentException.class, () ->
                tollService.axlePay(vehicleString, "2", "5.0"), "[AXLE TRUCK] Should throw when pay is not enough.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldThrowWhenVehicleTypeEXTRA_TRUCK_AXLE() {
        var vehicleString = "EXTRA_TRUCK_AXLE";
        Assertions.assertThrows(InvalidVehicleException.class, () ->
                tollService.standardPay(vehicleString, "100.0"), "[EXTRA_TRUCK_AXLE] Should throw when trying to use axle ENUM as vehicle.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldListPrices() {
        var tollMap = VehicleType.getTollMap();
        Assertions.assertEquals(tollService.getPrices(), tollMap);
    }

    @Test
    public void shouldAddProperlyToTollMap() {
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
        tollService.standardPay("BUS", "10");
        tollService.standardPay("BUS", "10");
        tollService.standardPay("MOTORCYCLE", "10");
        Assertions.assertEquals(tollService.getTollMap().size(), 2);
        Assertions.assertEquals(tollService.getTollMap().get(VehicleType.BUS), 2);
        Assertions.assertEquals(tollService.getTollMap().get(VehicleType.MOTORCYCLE), 1);
    }

    @Test
    public void shouldClearTollMap() {
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
        tollService.standardPay("BUS", "10");
        tollService.standardPay("BUS", "10");
        tollService.standardPay("MOTORCYCLE", "10");
        Assertions.assertEquals(tollService.getTollMap().size(), 2);
        Assertions.assertEquals(tollService.getTollMap().get(VehicleType.BUS), 2);
        Assertions.assertEquals(tollService.getTollMap().get(VehicleType.MOTORCYCLE), 1);
        tollService.clear();
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldThrowAtInvalidPaymentInput() {
        Assertions.assertThrows(InvalidPaymentException.class, () ->
                tollService.standardPay("BUS", "not a valid number"), "[PAYMENT] Should throw when pay is not valid.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }

    @Test
    public void shouldThrowAtInvalidAxleInput() {
        Assertions.assertThrows(InvalidAxleException.class, () ->
                tollService.axlePay("TRUCK", "invalid axle input", "100.0"), "[AXLE] " +
                "Should throw when axle is not valid.");
        Assertions.assertEquals(tollService.getTollMap().size(), 0);
    }
}
