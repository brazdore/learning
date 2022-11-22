package com.example.builder;

import com.example.entities.Contact;

public class ContactBuilder {

    private Contact contact;

    public ContactBuilder() {
        this.contact = new Contact();
    }

    public static ContactBuilder builder() {
        return new ContactBuilder();
    }

    public ContactBuilder withId(Long id) {
        this.contact.setId(id);
        return this;
    }

    public ContactBuilder withName(String name) {
        this.contact.setName(name);
        return this;
    }

    public ContactBuilder withEmail(String email) {
        this.contact.setEmail(email);
        return this;
    }

    public ContactBuilder withPhone(String phone) {
        this.contact.setPhone(phone);
        return this;
    }

    public Contact build() {
        return this.contact;
    }
}
