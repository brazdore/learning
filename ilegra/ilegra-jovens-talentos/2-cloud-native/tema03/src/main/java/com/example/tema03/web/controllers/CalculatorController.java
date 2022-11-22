package com.example.tema03.web.controllers;

import com.example.tema03.web.entities.Operation;
import com.example.tema03.web.services.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/calc")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(final CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping(value = "/add")
    public ResponseEntity<Double> sum(@RequestParam String x, @RequestParam String y) {
        Double i = calculatorService.addition(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/sub")
    public ResponseEntity<Double> subtract(@RequestParam String x, @RequestParam String y) {
        Double i = calculatorService.subtraction(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/mul")
    public ResponseEntity<Double> multiply(@RequestParam String x, @RequestParam String y) {
        Double i = calculatorService.multiplication(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/div")
    public ResponseEntity<Double> divide(@RequestParam String x, @RequestParam String y) {
        Double i = calculatorService.division(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/exp")
    public ResponseEntity<Double> power(@RequestParam String x, @RequestParam String y) {
        Double i = calculatorService.exponentiation(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/history")
    public ResponseEntity<List<Operation>> history() {
        return ResponseEntity.ok().body(calculatorService.getHistory());
    }

    @PostMapping(value = "/clear")
    public void clear() {
        calculatorService.clearAll();
    }
}
