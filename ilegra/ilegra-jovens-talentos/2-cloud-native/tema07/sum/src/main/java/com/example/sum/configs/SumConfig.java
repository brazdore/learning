package com.example.sum.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SumConfig implements CommandLineRunner {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Value("${TWITTER_URL}")
    private String TWITTER_URL;

    @Value("${GITHUB_URL}")
    private String GITHUB_URL;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void run(String... args) {
        System.out.println(ANSI_GREEN + "Twitter URL: " + TWITTER_URL);
        System.out.println(ANSI_GREEN + "GitHub URL: " + GITHUB_URL);
        System.out.print(ANSI_RESET);
    }
}
