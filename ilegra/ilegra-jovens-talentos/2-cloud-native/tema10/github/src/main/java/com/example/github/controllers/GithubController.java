package com.example.github.controllers;

import com.example.github.hystrix.HystrixGithubCountCommand;
import com.example.github.hystrix.HystrixGithubUserCommand;
import com.example.github.services.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/github")
public class GithubController {

    private final GithubService gitHubService;

    public GithubController(GithubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> start() {
        return ResponseEntity.ok().body("GithubController is running.");
    }

    @GetMapping(value = "/u/{username}")
    public ResponseEntity<String> user(@PathVariable String username) {
        return ResponseEntity.ok().body(new HystrixGithubUserCommand(username, gitHubService).execute());
    }

    @GetMapping(value = "/c/{username}")
    public ResponseEntity<Integer> count(@PathVariable String username) {
        return ResponseEntity.ok().body(new HystrixGithubCountCommand(username, gitHubService).execute());
    }
}
