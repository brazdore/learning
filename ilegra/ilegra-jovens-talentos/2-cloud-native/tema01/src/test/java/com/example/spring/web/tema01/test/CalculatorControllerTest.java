package com.example.spring.web.tema01.test;

import com.example.spring.web.tema01.controllers.CalculatorController;
import com.example.spring.web.tema01.entities.Operation;
import com.example.spring.web.tema01.enums.OperationType;
import com.example.spring.web.tema01.exceptions.CalculatorDivisionException;
import com.example.spring.web.tema01.exceptions.CalculatorInputException;
import com.example.spring.web.tema01.services.CalculatorService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    public void shouldPerformAddition() throws Exception {
        Mockito.when(calculatorService.addition("5", "3")).thenReturn(8D);

        String result = mvc.perform(MockMvcRequestBuilders.get("/calc/add")
                        .param("x", "5")
                        .param("y", "3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(calculatorService).addition("5", "3");

        assertEquals(String.valueOf(8D), result);
    }

    @Test
    public void shouldThrowWhenInvalidInputAtAddition() throws Exception {
        Mockito.when(calculatorService.addition("5", "batata")).thenThrow(new CalculatorInputException("Numbers-only."));

        mvc.perform(MockMvcRequestBuilders.get("/calc/add")
                        .param("x", "5")
                        .param("y", "batata")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> assertTrue(x.getResolvedException() instanceof CalculatorInputException))
                .andExpect(x -> assertEquals("Numbers-only.", x.getResolvedException().getMessage()));


        Mockito.verify(calculatorService).addition("5", "batata");
    }

    @Test
    public void shouldPerformSubtraction() throws Exception {
        Mockito.when(calculatorService.subtraction("10", "8")).thenReturn(2D);

        String result = mvc.perform(MockMvcRequestBuilders.get("/calc/sub")
                        .param("x", "10")
                        .param("y", "8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(calculatorService).subtraction("10", "8");

        assertEquals(String.valueOf(2D), result);
    }

    @Test
    public void shouldThrowWhenInvalidInputAtSubtraction() throws Exception {
        Mockito.when(calculatorService.subtraction("5", "tomate")).thenThrow(new CalculatorInputException("Numbers-only."));

        mvc.perform(MockMvcRequestBuilders.get("/calc/sub")
                        .param("x", "5")
                        .param("y", "tomate")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> assertTrue(x.getResolvedException() instanceof CalculatorInputException))
                .andExpect(x -> assertEquals("Numbers-only.", x.getResolvedException().getMessage()));


        Mockito.verify(calculatorService).subtraction("5", "tomate");
    }

    @Test
    public void shouldPerformMultiplication() throws Exception {
        Mockito.when(calculatorService.multiplication("15", "2")).thenReturn(30D);

        String result = mvc.perform(MockMvcRequestBuilders.get("/calc/mul")
                        .param("x", "15")
                        .param("y", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(calculatorService).multiplication("15", "2");

        assertEquals(String.valueOf(30D), result);
    }

    @Test
    public void shouldThrowWhenInvalidInputAtMultiplication() throws Exception {
        Mockito.when(calculatorService.multiplication("5", "pepino")).thenThrow(new CalculatorInputException("Numbers-only."));

        mvc.perform(MockMvcRequestBuilders.get("/calc/mul")
                        .param("x", "5")
                        .param("y", "pepino")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> assertTrue(x.getResolvedException() instanceof CalculatorInputException))
                .andExpect(x -> assertEquals("Numbers-only.", x.getResolvedException().getMessage()));

        Mockito.verify(calculatorService).multiplication("5", "pepino");
    }

    @Test
    public void shouldPerformDivision() throws Exception {
        Mockito.when(calculatorService.division("5", "5")).thenReturn(1D);

        String result = mvc.perform(MockMvcRequestBuilders.get("/calc/div")
                        .param("x", "5")
                        .param("y", "5"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(calculatorService).division("5", "5");

        assertEquals(String.valueOf(1D), result);
    }

    @Test
    public void shouldThrowWhenDivideByMinusZero() throws Exception {
        Mockito.when(calculatorService.division("10", "-0")).thenThrow(new CalculatorDivisionException("Cannot divide by [ZERO]."));

        mvc.perform(MockMvcRequestBuilders.get("/calc/div")
                        .param("x", "10")
                        .param("y", "-0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> assertTrue(x.getResolvedException() instanceof CalculatorDivisionException))
                .andExpect(x -> assertEquals("Cannot divide by [ZERO].", x.getResolvedException().getMessage()));

        Mockito.verify(calculatorService).division("10", "-0");
    }

    @Test
    public void shouldThrowWhenDivideByZero() throws Exception {
        Mockito.when(calculatorService.division("10", "0")).thenThrow(new CalculatorDivisionException("Cannot divide by [ZERO]."));

        mvc.perform(MockMvcRequestBuilders.get("/calc/div")
                        .param("x", "10")
                        .param("y", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> assertTrue(x.getResolvedException() instanceof CalculatorDivisionException))
                .andExpect(x -> assertEquals("Cannot divide by [ZERO].", x.getResolvedException().getMessage()));

        Mockito.verify(calculatorService).division("10", "0");
    }

    @Test
    public void shouldThrowWhenInvalidInputAtDivision() throws Exception {
        Mockito.when(calculatorService.division("5", "cenoura")).thenThrow(new CalculatorInputException("Numbers-only."));

        mvc.perform(MockMvcRequestBuilders.get("/calc/div")
                        .param("x", "5")
                        .param("y", "cenoura")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> assertTrue(x.getResolvedException() instanceof CalculatorInputException))
                .andExpect(x -> assertEquals("Numbers-only.", x.getResolvedException().getMessage()));

        Mockito.verify(calculatorService).division("5", "cenoura");
    }

    @Test
    public void shouldPerformExponentiation() throws Exception {
        Mockito.when(calculatorService.exponentiation("2", "3")).thenReturn(8D);

        String result = mvc.perform(MockMvcRequestBuilders.get("/calc/exp")
                        .param("x", "2")
                        .param("y", "3"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(calculatorService).exponentiation("2", "3");

        assertEquals(String.valueOf(8D), result);
    }

    @Test
    public void shouldThrowWhenInvalidInputAtExponentiation() throws Exception {
        Mockito.when(calculatorService.exponentiation("5", "brócolis")).thenThrow(new CalculatorInputException("Numbers-only."));

        mvc.perform(MockMvcRequestBuilders.get("/calc/exp")
                        .param("x", "5")
                        .param("y", "brócolis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(x -> assertTrue(x.getResolvedException() instanceof CalculatorInputException))
                .andExpect(x -> assertEquals("Numbers-only.", x.getResolvedException().getMessage()));


        Mockito.verify(calculatorService).exponentiation("5", "brócolis");
    }

    @Test
    public void shouldGetHistory() throws Exception {
        List<Operation> operationList = new ArrayList<>();
        operationList.add(new Operation(OperationType.ADDITION, List.of(2D, 3D)));
        operationList.add(new Operation(OperationType.EXPONENTIATION, List.of(2D, 2D)));

        Gson gson = new Gson();
        String json = gson.toJson(operationList);

        Mockito.when(calculatorService.getHistory()).thenReturn(operationList);

        String result = mvc.perform(MockMvcRequestBuilders.get("/calc/history"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(calculatorService).getHistory();

        assertEquals(json, result);
    }

    @Test
    public void shouldGetHistoryBlank() throws Exception {
        List<Operation> operationList = List.of();

        Gson gson = new Gson();
        String json = gson.toJson(operationList);

        Mockito.when(calculatorService.getHistory()).thenReturn(operationList);

        String result = mvc.perform(MockMvcRequestBuilders.get("/calc/history"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(calculatorService).getHistory();

        assertEquals(json, result);
    }

    @Test
    public void shouldClearAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/calc/clear"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        Mockito.verify(calculatorService).clearAll();
    }
}
