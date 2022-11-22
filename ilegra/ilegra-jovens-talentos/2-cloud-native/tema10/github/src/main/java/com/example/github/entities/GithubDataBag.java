package com.example.github.entities;

public class GithubDataBag {

    private final String username;
    private final String name;
    private final int repos;

    public GithubDataBag(String username, String name, int repos) {
        this.username = username;
        this.name = name;
        this.repos = repos;
    }
}
