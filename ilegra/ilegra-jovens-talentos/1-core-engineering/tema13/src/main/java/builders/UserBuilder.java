package builders;

import entities.User;

public class UserBuilder {

    private String name;
    private String email;
    private String phone;

    private UserBuilder() {
    }

    public static RequiredName name(String name) {
        return new UserBuilder.Builder(name);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public interface RequiredName {
        RequiredEmail email(String email);
    }

    public interface RequiredEmail {
        OptionalPhone phone(String phone);

        User build();
    }

    public interface OptionalPhone {
        User build();
    }

    private static class Builder implements RequiredName, RequiredEmail, OptionalPhone {
        private final UserBuilder instance = new UserBuilder();

        public Builder(String name) {
            instance.name = name;
        }

        @Override
        public RequiredEmail email(String email) {
            instance.email = email;
            return this;
        }

        @Override
        public OptionalPhone phone(String phone) {
            instance.phone = phone;
            return this;
        }

        @Override
        public User build() {
            return toUser();
        }

        private User toUser() {
            User user = new User();
            user.setName(instance.name);
            user.setEmail(instance.email);
            user.setPhone(instance.phone);
            return user;
        }
    }
}
