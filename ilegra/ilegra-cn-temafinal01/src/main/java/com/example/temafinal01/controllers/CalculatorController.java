package com.example.temafinal01.controllers;

import com.example.temafinal01.entities.Operation;
import com.example.temafinal01.services.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/calc")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> running() {
        return ResponseEntity.ok().body("CalculatorController is running.");
    }

    @GetMapping(value = "/add/{x}/{y}")
    public ResponseEntity<Double> add(@PathVariable String x, @PathVariable String y) {
        Double i = calculatorService.addition(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/sub/{x}/{y}")
    public ResponseEntity<Double> subtract(@PathVariable String x, @PathVariable String y) {
        Double i = calculatorService.subtraction(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/mul/{x}/{y}")
    public ResponseEntity<Double> multiply(@PathVariable String x, @PathVariable String y) {
        Double i = calculatorService.multiplication(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/div/{x}/{y}")
    public ResponseEntity<Double> divide(@PathVariable String x, @PathVariable String y) {
        Double i = calculatorService.division(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/exp/{x}/{y}")
    public ResponseEntity<Double> exponentiate(@PathVariable String x, @PathVariable String y) {
        Double i = calculatorService.exponentiation(x, y);
        return ResponseEntity.ok().body(i);
    }

    @GetMapping(value = "/history")
    public ResponseEntity<List<Operation>> history() {
        return ResponseEntity.ok().body(calculatorService.getHistory());
    }

    @PostMapping(value = "/clear")
    public void clear() {
        calculatorService.clear();
    }
}
