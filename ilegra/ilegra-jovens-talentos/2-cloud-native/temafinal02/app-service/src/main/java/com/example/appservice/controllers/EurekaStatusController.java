package com.example.appservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class EurekaStatusController {

    @GetMapping(value = "/Status")
    public ResponseEntity<HttpStatus> status() {
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
