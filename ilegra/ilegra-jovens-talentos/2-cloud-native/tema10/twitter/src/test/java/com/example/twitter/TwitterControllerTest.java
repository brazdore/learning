package com.example.twitter;

import com.example.twitter.controllers.TwitterController;
import com.example.twitter.services.TwitterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        var gura = "smol shork";

        Mockito.when(twitterService.getUserAsJSON(username)).thenReturn(gura);

        String result = mvc.perform(MockMvcRequestBuilders.get("/twitter/u/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(twitterService).getUserAsJSON(username);
        Assertions.assertEquals(gura, result);
    }

    @Test
    public void shouldCountTweetsController() throws Exception {
        var username = "gawrgura";
        var count = 3954;

        Mockito.when(twitterService.countTweets(username)).thenReturn(count);

        String result = mvc.perform(MockMvcRequestBuilders.get("/twitter/c/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(twitterService).countTweets(username);
        Assertions.assertEquals(count, Integer.valueOf(result));
    }
}
