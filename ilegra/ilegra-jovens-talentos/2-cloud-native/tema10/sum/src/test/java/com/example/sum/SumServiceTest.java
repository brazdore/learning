package com.example.sum;

import com.example.sum.exceptions.InvalidUsernameInputException;
import com.example.sum.exceptions.UserNotFoundException;
import com.example.sum.services.SumService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SumServiceTest {

    @MockBean
    private SumService sumService;

    @Test
    public void shouldGetSummary() {
        var watame = "watame potato chip";
        var username = "tsunomakiwatame";
        Mockito.when(sumService.getSummaryAsJSON(username)).thenReturn(watame);
        var finalUser = sumService.getSummaryAsJSON(username);
        Mockito.verify(sumService).getSummaryAsJSON(username);
        Assertions.assertEquals(watame, finalUser);
    }

    @Test
    public void shouldCountTweetsAndRepos() {
        var total = 12627;
        var username = "Sir Tweets-A-Lot";
        Mockito.when(sumService.countTweetsAndRepos(username)).thenReturn(total);
        var finalCount = sumService.countTweetsAndRepos(username);
        Mockito.verify(sumService).countTweetsAndRepos(username);
        Assertions.assertEquals(total, finalCount);
    }

    @Test
    public void shouldThrowWhenUserNotFound() {
        var username = "takamoriclara";
        Mockito.when(sumService.getSummary(username)).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () ->
                sumService.getSummary(username), "Should throw when [USER] NOT found.");
        Mockito.verify(sumService).getSummary(username);
    }

    @Test
    public void shouldThrowWhenInvalidUsernameInput() {
        var username = "235-90udegioh3i2ig nhe";
        Mockito.when(sumService.getSummary(username)).thenThrow(InvalidUsernameInputException.class);
        Assertions.assertThrows(InvalidUsernameInputException.class, () ->
                sumService.getSummary(username), "Should throw when [USER] is invalid.");
        Mockito.verify(sumService).getSummary(username);
    }
}
