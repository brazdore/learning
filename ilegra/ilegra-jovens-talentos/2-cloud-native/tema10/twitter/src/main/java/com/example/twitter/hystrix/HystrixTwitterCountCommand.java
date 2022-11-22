package com.example.twitter.hystrix;

import com.example.twitter.services.TwitterService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixTwitterCountCommand extends HystrixCommand<Integer> {

    private final String username;
    private final TwitterService twitterService;

    public HystrixTwitterCountCommand(String username, TwitterService twitterService) {
        super(HystrixCommandGroupKey.Factory.asKey("twitter"));
        this.username = username;
        this.twitterService = twitterService;
    }

    @Override
    protected Integer run() {
        return twitterService.countTweets(username);
    }
}
