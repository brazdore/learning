package com.example.github.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class GithubConfig implements CommandLineRunner {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Value("${GITHUB_TOKEN}")
    private String GITHUB_TOKEN;

    @Bean
    public GitHub gitHub() throws IOException {
        return new GitHubBuilder()
                .withOAuthToken(GITHUB_TOKEN)
                .build();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void run(String... args) {
        System.out.println(ANSI_GREEN + "GitHub Token: " + GITHUB_TOKEN);
        System.out.print(ANSI_RESET);
    }
}
