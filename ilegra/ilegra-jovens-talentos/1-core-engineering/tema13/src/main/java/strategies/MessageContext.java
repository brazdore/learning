package strategies;

public record MessageContext(MessageStrategy messageStrategy) {
    public String message() {
        return messageStrategy.message();
    }
}
