package strategies;

public class Email implements MessageStrategy {
    @Override
    public String message() {
        return "[EMAIL]";
    }
}
