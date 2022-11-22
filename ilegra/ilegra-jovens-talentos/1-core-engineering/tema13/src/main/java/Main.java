import builders.NotificationBuilder;
import builders.UserBuilder;
import entities.User;
import services.NotificationService;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {

        NotificationService notification = new NotificationService();

        User emailUser = UserBuilder.name("Takanashi Kiara").email("kfp@gmail.com").build();
        User phoneUser = UserBuilder.name("Calliope Mori").email("urboy@underworld.com").phone("9405945562").build();

        System.out.println(ANSI_YELLOW + notification.send(NotificationBuilder.to(emailUser).msg("KFP sales skyrocketed...").build()));
        System.out.println(ANSI_PURPLE + notification.send(NotificationBuilder.to(phoneUser).msg("Deadbeats are at it again...").build()));
        System.out.print(ANSI_RESET);
    }
}
