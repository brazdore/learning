package com.example.github.test;

import com.example.github.exceptions.GithubUserNotFoundException;
import com.example.github.exceptions.UnexpectedGithubException;
import com.example.github.services.GithubService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GithubServiceTest {

    @MockBean
    private GithubService githubService;

    @Test
    public void shouldGetUser() {
        var rushia = "rushia is cutting boa--";
        var username = "uruharushia";
        Mockito.when(githubService.getUser(username)).thenReturn(rushia);
        var finalUser = githubService.getUser(username);
        Mockito.verify(githubService).getUser(username);
        Assertions.assertEquals(rushia, finalUser);
    }

    @Test
    public void shouldCountRepos() {
        var repos = 2;
        var username = "oozorasubaru";
        Mockito.when(githubService.countRepos(username)).thenReturn(repos);
        var finalCount = githubService.countRepos(username);
        Mockito.verify(githubService).countRepos(username);
        Assertions.assertEquals(repos, finalCount);
    }

    @Test
    public void shouldThrowWhenUserNotFound() {
        var username = "takamoriclara";
        Mockito.when(githubService.getUser(username)).thenThrow(GithubUserNotFoundException.class);
        Assertions.assertThrows(GithubUserNotFoundException.class, () ->
                githubService.getUser(username), "Should throw when [GITHUB USER] NOT found.");
        Mockito.verify(githubService).getUser(username);
    }

    @Test
    public void shouldThrowUnexpectedException() {
        var username = "unexpectedExceptionHarbinger";
        Mockito.when(githubService.getUser(username)).thenThrow(UnexpectedGithubException.class);
        Assertions.assertThrows(UnexpectedGithubException.class, () ->
                githubService.getUser(username), "Should throw [UnexpectedGithubException] when [IOException].");
        Mockito.verify(githubService).getUser(username);
    }
}
