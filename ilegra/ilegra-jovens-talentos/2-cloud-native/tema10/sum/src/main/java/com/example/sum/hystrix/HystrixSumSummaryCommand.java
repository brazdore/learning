package com.example.sum.hystrix;

import com.example.sum.services.SumService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixSumSummaryCommand extends HystrixCommand<String> {

    private final String username;
    private final SumService sumService;

    public HystrixSumSummaryCommand(String username, SumService sumService) {
        super(HystrixCommandGroupKey.Factory.asKey("sum"));
        this.username = username;
        this.sumService = sumService;
    }

    @Override
    protected String run() {
        return sumService.getSummaryAsJSON(username);
    }
}
