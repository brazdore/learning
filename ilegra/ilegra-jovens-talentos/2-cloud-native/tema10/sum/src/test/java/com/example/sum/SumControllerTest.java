package com.example.sum;

import com.example.sum.controllers.SumController;
import com.example.sum.services.SumService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SumController.class)
public class SumControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SumService sumService;

    @Test
    public void shouldGetSummaryController() throws Exception {
        var username = "gawrgura";
        var description = "smol shork";

        Mockito.when(sumService.getSummaryAsJSON(username)).thenReturn(description);

        String result = mvc.perform(MockMvcRequestBuilders.get("/sum/u/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(sumService).getSummaryAsJSON(username);
        Assertions.assertEquals(description, result);
    }

    @Test
    public void shouldCountTweetsAndReposController() throws Exception {
        var username = "gawrgura";
        var count = 1248;

        Mockito.when(sumService.countTweetsAndRepos(username)).thenReturn(count);

        String result = mvc.perform(MockMvcRequestBuilders.get("/sum/c/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(sumService).countTweetsAndRepos(username);
        Assertions.assertEquals(count, Integer.valueOf(result));
    }
}
