package com.example.sum.services;

import com.example.sum.entities.SumDataBag;
import com.example.sum.exceptions.InvalidUsernameInputException;
import com.example.sum.exceptions.UserNotFoundException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.AbstractMap;

@Service
public class SumService {

    private final RestTemplate restTemplate;
    private final Gson gson;

    @Value("${TWITTER_URL}")
    private String TWITTER_URL;
    @Value("${GITHUB_URL}")
    private String GITHUB_URL;

    public SumService(RestTemplate restTemplate, Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    public Integer countTweetsAndRepos(String username) {
        var entry = getCountsOrThrow(username);
        return entry.getKey() + entry.getValue();
    }

    public String getSummary(String username) {
        var entry = getCountsOrThrow(username);
        return gson.toJson(new SumDataBag(username, entry.getKey(), entry.getValue()));
    }

    private AbstractMap.SimpleEntry<Integer, Integer> getCountsOrThrow(String username) {
        try {
            Integer tweets = restTemplate.getForObject(TWITTER_URL, Integer.class, username);
            Integer repos = restTemplate.getForObject(GITHUB_URL, Integer.class, username);
            return new AbstractMap.SimpleEntry<>(tweets, repos); // Key:Tweets & Value:Repos

        } catch (HttpClientErrorException.BadRequest e) { /// Problema: Como o RestTemplate do Twitter tá primeiro, ele checa primeiro o Twitter. Se não houver usuário lá, nem checa o GitHub.
            if (e.getResponseBodyAsString().contains("[INVALID GITHUB USERNAME]")) {
                throw new InvalidUsernameInputException("[GITHUB USER] input is NOT valid for [" + username + "].");
            } else throw new InvalidUsernameInputException("[TWITTER USER] input is NOT valid for [" + username + "].");

        } catch (HttpClientErrorException.NotFound e) {
            if (e.getResponseBodyAsString().contains("[GITHUB USER NOT FOUND]")) {
                throw new UserNotFoundException("[GITHUB USER] could NOT be found for [" + username + "].");
            } else throw new UserNotFoundException("[TWITTER USER] could NOT be found for [" + username + "].");
        }
    }
}

