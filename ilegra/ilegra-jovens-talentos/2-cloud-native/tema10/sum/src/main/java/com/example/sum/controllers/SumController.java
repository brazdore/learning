package com.example.sum.controllers;

import com.example.sum.hystrix.HystrixSumCountCommand;
import com.example.sum.hystrix.HystrixSumSummaryCommand;
import com.example.sum.services.SumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sum")
public class SumController {

    private final SumService sumService;

    public SumController(SumService sumService) {
        this.sumService = sumService;
    }

    @GetMapping
    public ResponseEntity<String> start() {
        return ResponseEntity.ok().body("SumController is running.");
    }

    @GetMapping(value = "/u/{username}")
    public ResponseEntity<String> summary(@PathVariable String username) {
        return ResponseEntity.ok().body(new HystrixSumSummaryCommand(username, sumService).execute());
    }

    @GetMapping(value = "/c/{username}")
    public ResponseEntity<Integer> count(@PathVariable String username) {
        return ResponseEntity.ok().body(new HystrixSumCountCommand(username, sumService).execute());
    }
}
