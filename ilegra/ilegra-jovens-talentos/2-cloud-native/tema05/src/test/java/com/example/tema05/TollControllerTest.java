package com.example.tema05;

import com.example.tema05.controllers.TollController;
import com.example.tema05.enums.VehicleType;
import com.example.tema05.exceptions.InvalidAxleException;
import com.example.tema05.exceptions.InvalidPaymentException;
import com.example.tema05.exceptions.InvalidVehicleException;
import com.example.tema05.exceptions.TollPaymentException;
import com.example.tema05.services.TollService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TollController.class)
class TollControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TollService tollService;

    @Test
    public void shouldPerformPaymentBUS() throws Exception {
        var v = "BUS";
        var p = "10";
        var change = BigDecimal.valueOf(Integer.parseInt(p)).subtract(BigDecimal.valueOf(VehicleType.valueOf(v).getTollPrice()));

        Mockito.when(tollService.standardPay(v, p)).thenReturn(change);

        String result = mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).standardPay(v, p);

        Assertions.assertEquals(String.valueOf(change), result);
    }

    @Test
    public void shouldPerformPaymentMOTORCYCLE() throws Exception {
        var v = "MOTORCYCLE";
        var p = "10";
        var change = BigDecimal.valueOf(Integer.parseInt(p)).subtract(BigDecimal.valueOf(VehicleType.valueOf(v).getTollPrice()));

        Mockito.when(tollService.standardPay(v, p)).thenReturn(change);

        String result = mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).standardPay(v, p);

        Assertions.assertEquals(String.valueOf(change), result);
    }

    @Test
    public void shouldPerformPaymentBIKE() throws Exception {
        var v = "BIKE";
        var p = "10";
        var change = BigDecimal.valueOf(Integer.parseInt(p)).subtract(BigDecimal.valueOf(VehicleType.valueOf(v).getTollPrice()));

        Mockito.when(tollService.standardPay(v, p)).thenReturn(change);

        String result = mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).standardPay(v, p);

        Assertions.assertEquals(String.valueOf(change), result);
    }

    @Test
    public void shouldPerformPaymentBEETLE() throws Exception {
        var v = "BEETLE";
        var p = "10";
        var change = BigDecimal.valueOf(Integer.parseInt(p)).subtract(BigDecimal.valueOf(VehicleType.valueOf(v).getTollPrice()));

        Mockito.when(tollService.standardPay(v, p)).thenReturn(change);

        String result = mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).standardPay(v, p);

        Assertions.assertEquals(String.valueOf(change), result);
    }

    @Test
    public void shouldPerformPaymentNormalTRUCK() throws Exception {
        var v = "TRUCK";
        var p = "10";
        var change = BigDecimal.valueOf(Integer.parseInt(p)).subtract(BigDecimal.valueOf(VehicleType.valueOf(v).getTollPrice()));

        Mockito.when(tollService.standardPay(v, p)).thenReturn(change);

        String result = mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).standardPay(v, p);

        Assertions.assertEquals(String.valueOf(change), result);
    }

    @Test
    public void shouldPerformPaymentAxleTRUCK() throws Exception {
        var v = "TRUCK";
        var p = "10";
        var a = "2";
        var axleValue = Integer.parseInt(a) * VehicleType.EXTRA_TRUCK_AXLE.getTollPrice();
        var change = BigDecimal.valueOf(Integer.parseInt(p)).subtract(BigDecimal.valueOf(VehicleType.valueOf(v).getTollPrice()))
                .subtract(BigDecimal.valueOf(axleValue));

        Mockito.when(tollService.axlePay(v, a, p)).thenReturn(change);

        String result = mvc.perform(MockMvcRequestBuilders.get("/toll/axle")
                        .param("v", v)
                        .param("p", p)
                        .param("a", a))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).axlePay(v, a, p);

        Assertions.assertEquals(String.valueOf(change), result);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsBUS() throws Exception {
        var v = "BUS";
        var p = "0.0";

        Mockito.when(tollService.standardPay(v, p)).thenThrow(new TollPaymentException("Not enough!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof TollPaymentException))
                .andExpect(x -> Assertions.assertEquals("Not enough!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).standardPay(v, p);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsMOTORCYCLE() throws Exception {
        var v = "MOTORCYCLE";
        var p = "0.0";

        Mockito.when(tollService.standardPay(v, p)).thenThrow(new TollPaymentException("Not enough!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof TollPaymentException))
                .andExpect(x -> Assertions.assertEquals("Not enough!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).standardPay(v, p);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsBIKE() throws Exception {
        var v = "BIKE";
        var p = "0.0";

        Mockito.when(tollService.standardPay(v, p)).thenThrow(new TollPaymentException("Not enough!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof TollPaymentException))
                .andExpect(x -> Assertions.assertEquals("Not enough!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).standardPay(v, p);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsBEETLE() throws Exception {
        var v = "BEETLE";
        var p = "0.0";

        Mockito.when(tollService.standardPay(v, p)).thenThrow(new TollPaymentException("Not enough!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof TollPaymentException))
                .andExpect(x -> Assertions.assertEquals("Not enough!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).standardPay(v, p);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsNormalTRUCK() throws Exception {
        var v = "TRUCK";
        var p = "0.0";

        Mockito.when(tollService.standardPay(v, p)).thenThrow(new TollPaymentException("Not enough!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof TollPaymentException))
                .andExpect(x -> Assertions.assertEquals("Not enough!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).standardPay(v, p);
    }

    @Test
    public void shouldThrowWhenNotEnoughFundsAxleTRUCK() throws Exception {
        var v = "TRUCK";
        var p = "0.0";
        var a = "2";

        Mockito.when(tollService.axlePay(v, a, p)).thenThrow(new TollPaymentException("Not enough!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/axle")
                        .param("v", v)
                        .param("p", p)
                        .param("a", a)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof TollPaymentException))
                .andExpect(x -> Assertions.assertEquals("Not enough!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).axlePay(v, a, p);
    }

    @Test
    public void shouldThrowAtInvalidVehicleInputStandard() throws Exception {
        var v = "not valid vehicle type";
        var p = "100";

        Mockito.when(tollService.standardPay(v, p)).thenThrow(new InvalidVehicleException("Invalid vehicle!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidVehicleException))
                .andExpect(x -> Assertions.assertEquals("Invalid vehicle!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).standardPay(v, p);
    }

    @Test
    public void shouldThrowAtInvalidVehicleInputAxle() throws Exception {
        var v = "BUS";
        var p = "100";
        var a = "2";

        Mockito.when(tollService.axlePay(v, a, p)).thenThrow(new InvalidVehicleException("Invalid vehicle!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/axle")
                        .param("v", v)
                        .param("p", p)
                        .param("a", a)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidVehicleException))
                .andExpect(x -> Assertions.assertEquals("Invalid vehicle!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).axlePay(v, a, p);
    }

    @Test
    public void shouldThrowAtInvalidPaymentInputStandard() throws Exception {
        var v = "BUS";
        var p = "invalid pay";

        Mockito.when(tollService.standardPay(v, p)).thenThrow(new InvalidPaymentException("Invalid pay!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidPaymentException))
                .andExpect(x -> Assertions.assertEquals("Invalid pay!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).standardPay(v, p);
    }

    @Test
    public void shouldThrowAtInvalidPaymentInputAxle() throws Exception {
        var v = "TRUCK";
        var p = "invalid pay";
        var a = "2";

        Mockito.when(tollService.axlePay(v, a, p)).thenThrow(new InvalidPaymentException("Invalid pay!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/axle")
                        .param("v", v)
                        .param("p", p)
                        .param("a", a)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidPaymentException))
                .andExpect(x -> Assertions.assertEquals("Invalid pay!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).axlePay(v, a, p);
    }

    @Test
    public void shouldThrowAtInvalidAxleInput() throws Exception {
        var v = "TRUCK";
        var p = "100";
        var a = "invalid axle";

        Mockito.when(tollService.axlePay(v, a, p)).thenThrow(new InvalidAxleException("Invalid axle!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/axle")
                        .param("v", v)
                        .param("p", p)
                        .param("a", a)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidAxleException))
                .andExpect(x -> Assertions.assertEquals("Invalid axle!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).axlePay(v, a, p);
    }

    @Test
    public void shouldThrowWhenUsingEXTRA_TRUCK_AXLE() throws Exception {
        var v = "EXTRA_TRUCK_AXLE";
        var p = "100";

        Mockito.when(tollService.standardPay(v, p)).thenThrow(new InvalidVehicleException("Invalid vehicle!"));

        mvc.perform(MockMvcRequestBuilders.get("/toll/standard")
                        .param("v", v)
                        .param("p", p)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidVehicleException))
                .andExpect(x -> Assertions.assertEquals("Invalid vehicle!", x.getResolvedException().getMessage()));

        Mockito.verify(tollService).standardPay(v, p);
    }

    @Test
    public void shouldListPrices() throws Exception {
        var tollMap = VehicleType.getTollMap();
        Mockito.when(tollService.getPrices()).thenReturn(tollMap);

        Gson gson = new Gson();
        String json = gson.toJson(tollMap);

        String result = mvc.perform(MockMvcRequestBuilders.get("/toll/prices"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).getPrices();

        Assertions.assertEquals(json, result);
    }

    @Test
    public void shouldReturnTollMap() throws Exception {
        var tollMap = Map.of(VehicleType.TRUCK, 2, VehicleType.BEETLE, 3);
        Mockito.when(tollService.getTollMap()).thenReturn(tollMap);

        Gson gson = new Gson();
        String json = gson.toJson(tollMap);

        String result = mvc.perform(MockMvcRequestBuilders.get("/toll/list"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).getTollMap();

        Assertions.assertEquals(json, result);
    }

    @Test
    public void shouldClearTollMap() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/toll/clear"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(tollService).clear();
    }
}
