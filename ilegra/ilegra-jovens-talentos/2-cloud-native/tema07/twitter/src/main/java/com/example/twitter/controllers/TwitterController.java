package com.example.twitter.controllers;

import com.example.twitter.services.TwitterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/twitter")
public class TwitterController {

    private final TwitterService twitterService;

    public TwitterController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<String> start() {
        return ResponseEntity.ok().body("TwitterController is running.");
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<String> user(@PathVariable String username) {
        return ResponseEntity.ok().body(twitterService.getUser(username));
    }

    @GetMapping(value = "/count/{username}")
    public ResponseEntity<Integer> count(@PathVariable String username) {
        return ResponseEntity.ok().body(twitterService.countTweets(username));
    }
}
