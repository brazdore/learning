package handlers;

import java.util.Objects;

public abstract class NotificationHandler {

    private final NotificationHandler nextHandler;

    public NotificationHandler() {
        this.nextHandler = null;
    }

    public NotificationHandler(NotificationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract String handle(Request request);

    protected String toNext(Request request) {
        if (Objects.nonNull(this.nextHandler)) {
            return this.nextHandler.handle(request);
        }
        return "";
    }
}
