package com.example.github.hystrix;

import com.example.github.services.GithubService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixGithubCountCommand extends HystrixCommand<Integer> {

    private final String username;
    private final GithubService githubService;

    public HystrixGithubCountCommand(String username, GithubService consumerService) {
        super(HystrixCommandGroupKey.Factory.asKey("github"));
        this.username = username;
        this.githubService = consumerService;
    }

    @Override
    protected Integer run() {
        return githubService.countRepos(username);
    }
}
