package strategies;

public class SMS implements MessageStrategy {
    @Override
    public String message() {
        return "[SMS]";
    }
}
