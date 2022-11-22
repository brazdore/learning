package handlers;

public class PushHandler extends NotificationHandler {

    public PushHandler() {
    }

    public PushHandler(NotificationHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public String handle(Request request) {
        if (RequestType.PUSH.equals(request.getType())) {
            return "[PUSH REQUEST] " + request.getMessage();
        }
        return toNext(request);

    }
}
