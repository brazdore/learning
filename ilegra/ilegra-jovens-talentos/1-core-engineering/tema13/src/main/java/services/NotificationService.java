package services;

import entities.User;
import strategies.Email;
import strategies.MessageContext;
import strategies.SMS;

import java.util.AbstractMap;

public class NotificationService {


    public NotificationService() {
    }

    public String send(AbstractMap.SimpleEntry<User, String> build) {
        MessageContext context;
        User user = build.getKey();
        String message = build.getValue();

        if (user.hasPhone()) {
            context = new MessageContext(new SMS());
        } else {
            context = new MessageContext(new Email());
        }
        return context.message() + " " + message;
    }
}

