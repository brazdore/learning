package com.example.twitter;

import com.example.twitter.exceptions.InvalidInputException;
import com.example.twitter.exceptions.TwitterUserNotFoundException;
import com.example.twitter.services.TwitterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TwitterServiceTest {

    @MockBean
    private TwitterService twitterService;

    @Test
    public void shouldGetUser() {
        var birb = "she is a birb indeed";
        var username = "takanashikiara";
        Mockito.when(twitterService.getUser(username)).thenReturn(birb);
        var finalUser = twitterService.getUser(username);
        Mockito.verify(twitterService).getUser(username);
        Assertions.assertEquals(birb, finalUser);
    }

    @Test
    public void shouldCountTweets() {
        var tweets = 3465;
        var username = "gawrgura";
        Mockito.when(twitterService.countTweets(username)).thenReturn(tweets);
        var finalCount = twitterService.countTweets(username);
        Mockito.verify(twitterService).countTweets(username);
        Assertions.assertEquals(tweets, finalCount);
    }

    @Test
    public void shouldThrowInvalidUserInput() {
        var username = "nínômae'ìnánís";
        Mockito.when(twitterService.getUser(username)).thenThrow(InvalidInputException.class);
        Assertions.assertThrows(InvalidInputException.class, () ->
                twitterService.getUser(username), "Should throw when [USERNAME] input is invalid.");
        Mockito.verify(twitterService).getUser(username);
    }

    @Test
    public void shouldThrowUserNotFound() {
        var username = "inainainaaathepotato4";
        Mockito.when(twitterService.getUser(username)).thenThrow(TwitterUserNotFoundException.class);
        Assertions.assertThrows(TwitterUserNotFoundException.class, () ->
                twitterService.getUser(username), "Should throw when [USERNAME] is NOT found.");
        Mockito.verify(twitterService).getUser(username);
    }
}
