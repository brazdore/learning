import com.example.entities.User;
import com.example.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.List;

public class UserServiceTest {

    UserService userService;
    User ame;

    {
        try {
            userService = new UserService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void init() throws IOException {
        userService.deleteAll();
        ame = new User(1L, "Amelia Watson");
        User kiara = new User(2L, "Takanashi Kiara");
        User nenechi = new User(3L, "Momosuzu Nene");
        userService.saveList(List.of(ame, kiara, nenechi));
    }

    @org.junit.jupiter.api.Test
    public void shouldAddUserToDB() throws IOException {
        Assertions.assertEquals(3, userService.getAll().size());
        User user = userService.save(new User(4L, "Gura"));
        Assertions.assertEquals(userService.getAll().get(3), user);
        Assertions.assertEquals(4, userService.getAll().size());
    }

    @org.junit.jupiter.api.Test
    public void shouldAddUserListToDB() throws IOException {
        Assertions.assertEquals(3, userService.getAll().size());
        List<User> newUsers = userService.saveList(List.of(new User(4L, "Gura"), new User(5L, "Polka")));
        List<User> repositoryLastTwo = userService.getAll().subList(3, 5);
        Assertions.assertEquals(newUsers, repositoryLastTwo);
        Assertions.assertEquals(5, userService.getAll().size());
    }

    @org.junit.jupiter.api.Test
    public void shouldFindUserByID() throws IOException {
        Assertions.assertEquals(userService.findUserByID(1L).get(), ame);
    }

    @org.junit.jupiter.api.Test
    public void shouldNotFindUserByID() throws IOException {
        Assertions.assertTrue(userService.findUserByID(15L).isEmpty());
    }

    @org.junit.jupiter.api.Test
    public void shouldIncreaseHoldingAndRented() throws IOException {
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 0);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 0);
        userService.increaseHoldingAndRented(1L, 10);
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 10);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 10);
    }

    @org.junit.jupiter.api.Test
    public void shouldDecreaseHolding() throws IOException {
        userService.increaseHoldingAndRented(1L, 10);
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 10);
        userService.decreaseHolding(1L, 1);
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 9);
    }

    @org.junit.jupiter.api.Test
    public void existsByID() throws IOException {
        Assertions.assertTrue(userService.existsByID(1L));
        Assertions.assertFalse(userService.existsByID(15L));
    }

    @org.junit.jupiter.api.Test
    public void shouldBeAbleToLoan() throws IOException {
        Assertions.assertTrue(userService.canLoanAmount(1L, 5, 5));
    }

    @org.junit.jupiter.api.Test
    public void shouldNotBeAbleToLoan() throws IOException {
        userService.increaseHoldingAndRented(1L, 4);
        Assertions.assertFalse(userService.canLoanAmount(1L, 5, 5));
        Assertions.assertFalse(userService.canLoanAmount(2L, 5, 4));
    }

    @org.junit.jupiter.api.Test
    public void shouldListTopUsersCorrectly() throws IOException {
        Assertions.assertEquals(userService.getHoldingAmount(1L), 0);
        Assertions.assertEquals(userService.getHoldingAmount(2L), 0);
        Assertions.assertEquals(userService.getHoldingAmount(3L), 0);
        userService.increaseHoldingAndRented(3L, 5);
        userService.increaseHoldingAndRented(1L, 3);
        List<User> properOrderList = List.of(userService.findUserByID(3L).get(),
                userService.findUserByID(1L).get(), userService.findUserByID(2L).get());
        Assertions.assertEquals(properOrderList, userService.topUsers(3));
    }
}
