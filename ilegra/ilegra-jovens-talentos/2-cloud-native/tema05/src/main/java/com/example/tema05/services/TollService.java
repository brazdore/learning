package com.example.tema05.services;

import com.example.tema05.enums.VehicleType;
import com.example.tema05.exceptions.InvalidAxleException;
import com.example.tema05.exceptions.InvalidPaymentException;
import com.example.tema05.exceptions.InvalidVehicleException;
import com.example.tema05.exceptions.TollPaymentException;
import com.example.tema05.repositories.TollRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Service
public class TollService {

    private final TollRepository tollRepository;

    public TollService(final TollRepository tollRepository) {
        this.tollRepository = tollRepository;
    }

    public Map<VehicleType, Integer> getTollMap() {
        return tollRepository.getTollMap();
    }

    public BigDecimal standardPay(String vehicleString, String paid) {
        var doublePaid = convertToDoubleOrThrow(paid);
        vehicleString = vehicleString.toUpperCase();

        if (!VehicleType.isValid(vehicleString)) {
            throw new InvalidVehicleException("Invalid [VEHICLE TYPE]: {" + vehicleString + "}.");
        } else if (VehicleType.valueOf(vehicleString).equals(VehicleType.EXTRA_TRUCK_AXLE)) {
            throw new InvalidVehicleException("Invalid [VEHICLE TYPE]: {" + vehicleString + "} cannot be used as a vehicle.");
        }

        var toPay = getAmountToPay(vehicleString, null);

        return payAndSave(doublePaid, toPay, vehicleString);
    }

    public BigDecimal axlePay(String vehicleString, String axleAmount, String paid) {
        int integerAxle;

        try {
            if (Integer.parseInt(axleAmount) >= 1) {
                integerAxle = Integer.parseInt(axleAmount);
            } else throw new InvalidAxleException("Invalid [AXLE]: {" + axleAmount + "} cannot be NEGATIVE or ZERO.");
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidAxleException("Invalid [AXLE]: {" + axleAmount + "} is not a valid number.");
        }

        var doublePaid = convertToDoubleOrThrow(paid);
        vehicleString = vehicleString.toUpperCase();

        if (!VehicleType.isTruck(vehicleString)) {
            throw new InvalidVehicleException("Invalid [VEHICLE TYPE]: {" + vehicleString + "} should be [TRUCK].");
        }

        var toPay = getAmountToPay(vehicleString, integerAxle);
        return payAndSave(doublePaid, toPay, vehicleString);
    }

    public Map<VehicleType, Double> getPrices() {
        return VehicleType.getTollMap();
    }

    public void clear() {
        tollRepository.clear();
    }

    private Double getAmountToPay(String vehicleString, Integer axleAmount) {
        var basePrice = VehicleType.valueOf(vehicleString).getTollPrice();
        if (VehicleType.valueOf(vehicleString).equals(VehicleType.TRUCK) && Objects.nonNull(axleAmount)) {
            basePrice += VehicleType.EXTRA_TRUCK_AXLE.getTollPrice() * axleAmount;
        }
        return basePrice;
    }

    private BigDecimal payAndSave(Double paid, Double toPay, String vehicleType) {
        if (paid >= toPay) {
            tollRepository.save(VehicleType.valueOf(vehicleType));
            return BigDecimal.valueOf(paid).subtract(BigDecimal.valueOf(toPay));
        } else {
            throw new TollPaymentException("Not enough! Cost: " + toPay + ", Paid: " + paid);
        }
    }

    private Double convertToDoubleOrThrow(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidPaymentException("Invalid [PAID]: {" + s + "} is not a valid number.");
        }
    }
}
