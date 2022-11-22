package com.example.twitter.services;

import com.example.twitter.entities.TwitterDataBag;
import com.example.twitter.exceptions.InvalidInputException;
import com.example.twitter.exceptions.TwitterUserNotFoundException;
import com.google.gson.Gson;
import io.github.redouane59.twitter.TwitterClient;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class TwitterService {

    private static final String TWITTER_REGEX = "^[a-z0-9_]+$";

    private final TwitterClient twitterClient;
    private final Gson gson;

    public TwitterService(TwitterClient twitterClient, Gson gson) {
        this.twitterClient = twitterClient;
        this.gson = gson;
    }

    public String getUser(String username) {
        throwWhenInvalidInputOrEmptyUser(username);
        var user = twitterClient.getUserFromUserName(username);
        return gson.toJson(new TwitterDataBag(user.getName(), user.getDisplayedName(), user.getId(), user.getTweetCount(),
                user.getFollowersCount(), user.getFollowingCount(), user.getProfileImageUrl()));
    }

    public Integer countTweets(String username) {
        throwWhenInvalidInputOrEmptyUser(username);
        return twitterClient.getUserFromUserName(username).getTweetCount();
    }

    private void throwWhenInvalidInputOrEmptyUser(String username) {
        Pattern p = Pattern.compile(TWITTER_REGEX, Pattern.CASE_INSENSITIVE);
        boolean isValidInput = p.matcher(username).find();

        if (!isValidInput) {
            throw new InvalidInputException("Username [" + username + "] input is NOT valid.");
        }
        if (Objects.equals(twitterClient.getUserFromUserName(username).getId(), null)) {
            throw new TwitterUserNotFoundException("Username [" + username + "] could NOT be found.");
        }
    }
}
