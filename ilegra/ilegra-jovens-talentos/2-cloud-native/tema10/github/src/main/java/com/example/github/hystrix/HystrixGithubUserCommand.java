package com.example.github.hystrix;

import com.example.github.services.GithubService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixGithubUserCommand extends HystrixCommand<String> {

    private final String username;
    private final GithubService githubService;

    public HystrixGithubUserCommand(String username, GithubService consumerService) {
        super(HystrixCommandGroupKey.Factory.asKey("github"));
        this.username = username;
        this.githubService = consumerService;
    }

    @Override
    protected String run() {
        return githubService.getUserAsJSON(username);
    }
}
