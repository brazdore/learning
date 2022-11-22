package com.github.diegopacheco.sandbox.java.netflixoss.karyon.rest;

import com.github.diegopacheco.sandbox.java.netflixoss.karyon.ribbon.RibbonMathClient;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class CalcService {

    private final RibbonMathClient client;

    @Inject
    public CalcService(RibbonMathClient client) {
        this.client = client;
    }

    public Double calc(String op, Double x, Double y) {

        List<String> opList = Arrays.asList("sum", "sub", "div", "mul", "exp");

        if (StringUtils.isBlank(op) || !opList.contains(op)) {
            throw new IllegalArgumentException("You must pass a valid expression; [" + op + "] is NOT valid.");
        }

        Double result = 0.0;

        switch (op) {
            case "sum":
                result = client.sum(x, y).toBlocking().first();
                break;
            case "sub":
                result = client.sub(x, y).toBlocking().first();
                break;
            case "div":
                result = client.div(x, y).toBlocking().first();
                break;
            case "mul":
                result = client.mul(x, y).toBlocking().first();
                break;
            case "exp":
                result = client.exp(x, y).toBlocking().first();
                break;
        }
        return result;
    }
}

