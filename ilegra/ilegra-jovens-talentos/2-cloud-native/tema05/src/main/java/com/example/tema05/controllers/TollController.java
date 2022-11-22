package com.example.tema05.controllers;

import com.example.tema05.enums.VehicleType;
import com.example.tema05.services.TollService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(value = "/toll")
public class TollController {

    private final TollService tollService;

    public TollController(final TollService tollService) {
        this.tollService = tollService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> start() {
        return ResponseEntity.ok().body("Toll is running.");
    }

    @GetMapping(value = "/standard")
    public ResponseEntity<BigDecimal> standardPay(@RequestParam(value = "v") String s, @RequestParam(value = "p") String y) {
        return ResponseEntity.ok().body(tollService.standardPay(s, y));
    }

    @GetMapping(value = "/axle")
    public ResponseEntity<BigDecimal> axlePay(@RequestParam(value = "v") String s, @RequestParam(value = "a") String axle,
                                              @RequestParam(value = "p") String y) {
        return ResponseEntity.ok().body(tollService.axlePay(s, axle, y));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Map<VehicleType, Integer>> list() {
        return ResponseEntity.ok().body(tollService.getTollMap());
    }

    @GetMapping(value = "/prices")
    public ResponseEntity<Map<VehicleType, Double>> prices() {
        return ResponseEntity.ok().body(tollService.getPrices());
    }

    @PostMapping(value = "/clear")
    public void clear() {
        tollService.clear();
    }
}
