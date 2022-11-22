import handlers.*;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {

        NotificationHandler pushHandler = new PushHandler();
        NotificationHandler emailHandler = new EmailHandler(pushHandler);
        NotificationHandler smsHandler = new SMSHandler(emailHandler);

        Request r1 = new Request("ogey peko", RequestType.EMAIL);
        Request r2 = new Request("AHOY!", RequestType.SMS);
        Request r3 = new Request("waht a degenelate", RequestType.PUSH);

        System.out.println(ANSI_YELLOW + smsHandler.handle(r1));
        System.out.println(ANSI_PURPLE + smsHandler.handle(r2));
        System.out.println(ANSI_BLUE + smsHandler.handle(r3));

        System.out.print(ANSI_RESET);
    }
}
