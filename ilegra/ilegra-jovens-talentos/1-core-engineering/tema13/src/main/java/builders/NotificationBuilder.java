package builders;

import entities.User;

import java.util.AbstractMap;

public class NotificationBuilder {

    private User user;
    private String msg;

    private NotificationBuilder() {
    }

    public static RequiredUser to(User user) {
        return new NotificationBuilder.Builder(user);
    }

    public User getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }

    private String greetings() {
        return "Hihiho, " + user.getName() + ". ";
    }

    public interface RequiredUser {
        RequiredMsg msg(String msg);
    }

    public interface RequiredMsg {
        AbstractMap.SimpleEntry<User, String> build();
    }

    private static class Builder implements RequiredUser, RequiredMsg {
        private final NotificationBuilder instance = new NotificationBuilder();

        public Builder(User user) {
            instance.user = user;
        }

        @Override
        public RequiredMsg msg(String msg) {
            instance.msg = msg;
            return this;
        }

        @Override
        public AbstractMap.SimpleEntry<User, String> build() {
            instance.msg = instance.greetings() + instance.getMsg();
            return new AbstractMap.SimpleEntry<>(instance.getUser(), instance.getMsg());
        }
    }
}
