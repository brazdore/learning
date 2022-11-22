package handlers;

public class EmailHandler extends NotificationHandler {

    public EmailHandler() {
    }

    public EmailHandler(NotificationHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String handle(Request request) {
        if (RequestType.EMAIL.equals(request.getType())) {
            return "[EMAIL REQUEST] " + request.getMessage();
        }
        return toNext(request);
    }
}
