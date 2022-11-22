package com.example.sum;

import com.example.sum.controllers.SumController;
import com.example.sum.exceptions.InvalidUsernameInputException;
import com.example.sum.exceptions.UserNotFoundException;
import com.example.sum.services.SumService;
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

        Mockito.when(sumService.getSummary(username)).thenReturn(description);

        String result = mvc.perform(MockMvcRequestBuilders.get("/sum/summary/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(sumService).getSummary(username);
        Assertions.assertEquals(description, result);
    }

    @Test
    public void shouldCountTweetsAndReposController() throws Exception {
        var username = "gawrgura";
        var count = 1248;

        Mockito.when(sumService.countTweetsAndRepos(username)).thenReturn(count);

        String result = mvc.perform(MockMvcRequestBuilders.get("/sum/count/{username}", username))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Mockito.verify(sumService).countTweetsAndRepos(username);
        Assertions.assertEquals(count, Integer.valueOf(result));
    }

    @Test
    public void shouldThrowWhenUserNotFoundController() throws Exception {
        var username = "sneaky_botan";

        Mockito.when(sumService.getSummary(username)).thenThrow(new UserNotFoundException("notfound-3784"));

        mvc.perform(MockMvcRequestBuilders.get("/sum/summary/{username}", username))
                .andExpect(status().isNotFound())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof UserNotFoundException))
                .andExpect(x -> Assertions.assertEquals("notfound-3784", Objects.requireNonNull(x.getResolvedException()).getMessage()));

        Mockito.verify(sumService).getSummary(username);
    }

    @Test
    public void shouldThrowWhenInvalidUsernameInputController() throws Exception {
        var username = "12-90458sdfhjkj32hrjd shlksjdfk3k4hui2kjwdhfkj@";

        Mockito.when(sumService.getSummary(username)).thenThrow(new InvalidUsernameInputException("input-5802"));

        mvc.perform(MockMvcRequestBuilders.get("/sum/summary/{username}", username))
                .andExpect(status().isBadRequest())
                .andExpect(x -> Assertions.assertTrue(x.getResolvedException() instanceof InvalidUsernameInputException))
                .andExpect(x -> Assertions.assertEquals("input-5802", Objects.requireNonNull(x.getResolvedException()).getMessage()));

        Mockito.verify(sumService).getSummary(username);
    }
}
