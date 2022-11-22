package com.example.twitter.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TwitterConfig implements CommandLineRunner {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Value("${CONSUMER_KEY}")
    private String CONSUMER_KEY;

    @Value("${CONSUMER_SECRET}")
    private String CONSUMER_SECRET;

    @Bean
    public TwitterClient twitterClient() {
        return new TwitterClient(TwitterCredentials.builder()
                .apiKey(CONSUMER_KEY)
                .apiSecretKey(CONSUMER_SECRET)
                .build());
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void run(String... args) {
        System.out.println(ANSI_GREEN + "Twitter CONSUMER KEY: " + CONSUMER_KEY);
        System.out.println(ANSI_GREEN + "Twitter CONSUMER SECRET: " + CONSUMER_SECRET);
        System.out.print(ANSI_RESET);
    }
}
