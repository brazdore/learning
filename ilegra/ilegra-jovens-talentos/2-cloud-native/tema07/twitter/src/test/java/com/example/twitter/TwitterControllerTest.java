package com.example.twitter;

import com.example.twitter.controllers.TwitterController;
import com.example.twitter.exceptions.InvalidInputException;
import com.example.twitter.exceptions.TwitterUserNotFoundException;
import com.example.twitter.services.TwitterService;
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

@WebMvcTest(TwitterController.class)
public class TwitterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TwitterService twitterService;

    @Test
    public void shouldGetUserController() throws Exception {
        var username = "gawrgura";
        var description = "smol shork";

        Mockito.when(twitterService.getUser(username)).thenReturn(description);

        String result = mvc.perform(MockMvcRequestBuilders.get("/twitter/user/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(twitterService).getUser(username);
        Assertions.assertEquals(description, result);
    }

    @Test
    public void shouldCountTweetsController() throws Exception {
        var username = "gawrgura";
        var count = 3954;

        Mockito.when(twitterService.countTweets(username)).thenReturn(count);

        String result = mvc.perform(MockMvcRequestBuilders.get("/twitter/count/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(twitterService).countTweets(username);
        Assertions.assertEquals(count, Integer.valueOf(result));
    }

    @Test
    public void shouldThrowInvalidUserInputController() throws Exception {
        var username = "nínômae'ìnánís";

        Mockito.when(twitterService.getUser(username)).thenThrow(new InvalidInputException("input-9049"));

        mvc.perform(MockMvcRequestBuilders.get("/twitter/user/{username}", username))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidInputException))
                .andExpect(x -> Assertions.assertEquals("input-9049", Objects.requireNonNull(x.getResolvedException()).getMessage()));

        Mockito.verify(twitterService).getUser(username);
    }

    @Test
    public void shouldThrowUserNotFoundController() throws Exception {
        var username = "inainainaaathepotato4";

        Mockito.when(twitterService.getUser(username)).thenThrow(new TwitterUserNotFoundException("input-4558"));

        mvc.perform(MockMvcRequestBuilders.get("/twitter/user/{username}", username))
                .andExpect(status().isNotFound())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof TwitterUserNotFoundException))
                .andExpect(x -> Assertions.assertEquals("input-4558", Objects.requireNonNull(x.getResolvedException()).getMessage()));

        Mockito.verify(twitterService).getUser(username);
    }
}
