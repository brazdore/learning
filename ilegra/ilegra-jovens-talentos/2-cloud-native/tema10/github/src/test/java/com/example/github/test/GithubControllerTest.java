package com.example.github.test;

import com.example.github.controllers.GithubController;
import com.example.github.entities.GithubDataBag;
import com.example.github.exceptions.GithubUserNotFoundException;
import com.example.github.exceptions.UnexpectedGithubException;
import com.example.github.services.GithubService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GithubController.class)
public class GithubControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GithubService githubService;

    @Test
    public void shouldGetUserController() throws Exception {
        var username = "gawrgura";
        var gura = "ur smol local shark";

        Mockito.when(githubService.getUserAsJSON(username)).thenReturn(gura);

        var result = mvc.perform(MockMvcRequestBuilders.get("/github/u/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(githubService).getUserAsJSON(username);
        Assertions.assertEquals(gura, result);
    }

    @Test
    public void shouldCountReposController() throws Exception {
        var username = "gawrgura";
        var count = 12;

        Mockito.when(githubService.countRepos(username)).thenReturn(count);

        String result = mvc.perform(MockMvcRequestBuilders.get("/github/c/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(githubService).countRepos(username);
        Assertions.assertEquals(count, Integer.valueOf(result));
    }
}
