package handlers;

public class SMSHandler extends NotificationHandler {

    public SMSHandler() {
    }

    public SMSHandler(NotificationHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String handle(Request request) {
        if (RequestType.SMS.equals(request.getType())) {
            return "[SMS REQUEST] " + request.getMessage();
        }
        return toNext(request);
    }
}
