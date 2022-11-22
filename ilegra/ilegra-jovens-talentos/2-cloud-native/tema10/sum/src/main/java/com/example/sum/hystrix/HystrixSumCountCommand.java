package com.example.sum.hystrix;

import com.example.sum.services.SumService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixSumCountCommand extends HystrixCommand<Integer> {

    private final String username;
    private final SumService sumService;

    public HystrixSumCountCommand(String username, SumService sumService) {
        super(HystrixCommandGroupKey.Factory.asKey("sum"));
        this.username = username;
        this.sumService = sumService;
    }

    @Override
    protected Integer run() {
        return sumService.countTweetsAndRepos(username);
    }
}
