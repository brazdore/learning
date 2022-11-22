import builders.UserBuilder;
import entities.User;
import exceptions.UserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserBuilderTest {

    @Test
    public void shouldCreateProperUserWithAllFields() {
        String name = "Gawr Gura";
        String email = "gura@atlantis.com";
        String phone = "495934043";

        User buildUser = UserBuilder.name(name).email(email).phone(phone).build();
        User normalUser = new User();
        normalUser.setName(name);
        normalUser.setEmail(email);
        normalUser.setPhone(phone);

        assertEquals(buildUser, normalUser);
    }

    @Test
    public void shouldCreateProperUserWithNameAndEmail() {
        String name = "Gawr Gura";
        String email = "gura@atlantis.com";

        User buildUser = UserBuilder.name(name).email(email).build();
        User normalUser = new User();
        normalUser.setName(name);
        normalUser.setEmail(email);

        assertEquals(buildUser, normalUser);
    }

    @Test
    public void shouldThrowWhenUserWithInvalidName() {
        assertThrows(UserException.class, () ->
                UserBuilder.name("    ").email("valid@email.com").build(), "Should throw when [NAME] is empty or whitespace-only.");
    }

    @Test
    public void shouldThrowWhenUserWithInvalidEmail() {
        assertThrows(UserException.class, () ->
                UserBuilder.name("Valid Name").email("    ").build(), "Should throw when [EMAIL] is empty or whitespace-only.");
    }
}
