package com.example.sum.entities;

public class SumDataBag {

    private final String username;
    private final int tweets;
    private final int repos;

    public SumDataBag(String username, int tweets, int repos) {
        this.username = username;
        this.tweets = tweets;
        this.repos = repos;
    }
}
