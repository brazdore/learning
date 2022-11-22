package com.example.github.test;

import com.example.github.controllers.GithubController;
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
        var description = "smol shork";

        Mockito.when(githubService.getUser(username)).thenReturn(description);

        String result = mvc.perform(MockMvcRequestBuilders.get("/github/user/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(githubService).getUser(username);
        Assertions.assertEquals(description, result);
    }

    @Test
    public void shouldCountReposController() throws Exception {
        var username = "gawrgura";
        var count = 12;

        Mockito.when(githubService.countRepos(username)).thenReturn(count);

        String result = mvc.perform(MockMvcRequestBuilders.get("/github/count/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(githubService).countRepos(username);
        Assertions.assertEquals(count, Integer.valueOf(result));
    }

    @Test
    public void shouldThrowWhenUserNotFoundController() throws Exception {
        var username = "sneaky_botan";

        Mockito.when(githubService.getUser(username)).thenThrow(new GithubUserNotFoundException("notfounc-4042"));

        mvc.perform(MockMvcRequestBuilders.get("/github/user/{username}", username))
                .andExpect(status().isNotFound())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof GithubUserNotFoundException))
                .andExpect(x -> Assertions.assertEquals("notfounc-4042", Objects.requireNonNull(x.getResolvedException()).getMessage()));

        Mockito.verify(githubService).getUser(username);
    }

    @Test
    public void shouldThrowUnexpectedExceptionController() throws Exception {
        var username = "chaotic_polka";

        Mockito.when(githubService.getUser(username)).thenThrow(new UnexpectedGithubException("unexpected-2020"));

        mvc.perform(MockMvcRequestBuilders.get("/github/user/{username}", username))
                .andExpect(status().isIAmATeapot())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof UnexpectedGithubException))
                .andExpect(x -> Assertions.assertEquals("unexpected-2020", Objects.requireNonNull(x.getResolvedException()).getMessage()));

        Mockito.verify(githubService).getUser(username);
    }
}
