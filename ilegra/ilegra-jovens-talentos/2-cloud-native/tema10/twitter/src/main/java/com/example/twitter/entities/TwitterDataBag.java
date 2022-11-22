package com.example.twitter.entities;

public class TwitterDataBag {

    private final String name;
    private final String displayedName;
    private final String id;
    private final int tweetCount;
    private final int followersCount;
    private final int followingCount;
    private final String profilePicURL;

    public TwitterDataBag(String name, String displayedName, String id, int tweetCount, int followersCount, int followingCount, String profilePicURL) {
        this.name = name;
        this.displayedName = displayedName;
        this.id = id;
        this.tweetCount = tweetCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.profilePicURL = profilePicURL;
    }
}
