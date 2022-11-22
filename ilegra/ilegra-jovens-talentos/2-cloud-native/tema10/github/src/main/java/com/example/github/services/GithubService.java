package com.example.github.services;

import com.example.github.entities.GithubDataBag;
import com.example.github.exceptions.GithubUserNotFoundException;
import com.example.github.exceptions.UnexpectedGithubException;
import com.google.gson.Gson;
import org.kohsuke.github.GHFileNotFoundException;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GithubService {

    private final GitHub gitHub;
    private final Gson gson;

    public GithubService(GitHub gitHub, Gson gson) {
        this.gitHub = gitHub;
        this.gson = gson;
    }

    public GithubDataBag getUser(String username) {
        var user = getUserOrThrowWhenNotFound(username);
        try {
            return new GithubDataBag(username, user.getName(), user.getPublicRepoCount());
        } catch (IOException e) {
            throw new UnexpectedGithubException("An unexpected error occurred when retrieving data from [" + username + "]. Error: " + e.getMessage());
        }
    }

    public String getUserAsJSON(String username) {
        return gson.toJson(getUser(username));
    }

    public Integer countRepos(String username) {
        try {
            return getUserOrThrowWhenNotFound(username).getPublicRepoCount();
        } catch (IOException e) {
            throw new UnexpectedGithubException("An unexpected error occurred when retrieving data from [" + username + "]. Error: " + e.getMessage());
        }
    }

    private GHUser getUserOrThrowWhenNotFound(String username) {
        try {
            return gitHub.getUser(username);
        } catch (GHFileNotFoundException e) {
            throw new GithubUserNotFoundException("User: [" + username + "] could NOT be found");
        } catch (IOException e) { // ?
            throw new UnexpectedGithubException("An unexpected error occurred when searching for [" + username + "]. Error: " + e.getMessage());
        }
    }
}
