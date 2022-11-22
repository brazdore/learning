import builders.NotificationBuilder;
import builders.UserBuilder;
import entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import services.NotificationService;

public class NotificationTest {

    @Test
    public void shouldSendPhoneNotification() {
        NotificationService notificationService = new NotificationService();

        User phoneUser = UserBuilder
                .name("Gawr Gura")
                .email("gura@atlantis.com")
                .phone("390248532")
                .build();

        String notificationString = notificationService.send(NotificationBuilder.to(phoneUser).msg("Shrimp?").build());

        Assertions.assertTrue(notificationString.contains("[SMS]"));
        Assertions.assertTrue(notificationString.contains("Gawr Gura"));
        Assertions.assertTrue(notificationString.contains("Shrimp?"));
        Assertions.assertFalse(notificationString.contains("[EMAIL]"));
    }

    @Test
    public void shouldSendEmailNotification() {
        NotificationService notificationService = new NotificationService();

        User phoneUser = UserBuilder
                .name("Ninomae Ina'nis")
                .email("therealina@tako.org")
                .build();

        String notificationString = notificationService.send(NotificationBuilder.to(phoneUser).msg("Cookie plz...").build());

        Assertions.assertTrue(notificationString.contains("[EMAIL]"));
        Assertions.assertTrue(notificationString.contains("Ninomae Ina'nis"));
        Assertions.assertTrue(notificationString.contains("Cookie plz..."));
        Assertions.assertFalse(notificationString.contains("[SMS]"));
    }
}

