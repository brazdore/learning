package com.example.twitter.hystrix;

import com.example.twitter.services.TwitterService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixTwitterUserCommand extends HystrixCommand<String> {

    private final String username;
    private final TwitterService twitterService;

    public HystrixTwitterUserCommand(String username, TwitterService twitterService) {
        super(HystrixCommandGroupKey.Factory.asKey("twitter"));
        this.username = username;
        this.twitterService = twitterService;
    }

    @Override
    protected String run() {
        return twitterService.getUserAsJSON(username);
    }
}
