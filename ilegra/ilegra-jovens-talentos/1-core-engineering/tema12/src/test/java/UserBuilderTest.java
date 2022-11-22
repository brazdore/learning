import com.example.builders.UserBuilder;
import com.example.entities.Address;
import com.example.entities.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserBuilderTest {

    @Test
    public void shouldCreateProperUser() {
        Date now = Date.from(Instant.now());

        User kiara = new User("Takanashi Kiara", now, new Address("KFP Street", 190, "Holocity", "Pekoland"));

        User kiaraBuilder = UserBuilder.name("Takanashi Kiara")
                .birthDate(now)
                .streetName("KFP Street")
                .streetNumber(190)
                .city("Holocity")
                .state("Pekoland")
                .build();

        assertEquals(kiara, kiaraBuilder);
    }
}
