package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContactService {

    public static final String ANSI_RED = "\u001B[31m";

    private List<Contact> list = new ArrayList<>();

    public List<Contact> getList() {
        return list;
    }

    public List<Contact> listAll() {
        if (list.size() > 0) {
            return list;
        } else {
            throw new ContactException(ANSI_RED + "No contacts were found. You should make some friends...");
        }
    }

    public Contact findByID(int id) {
        List<Contact> contacts = list.stream()
                .filter(x -> Objects.equals(x.getId(), id))
                .collect(Collectors.toList());

        if (contacts.size() > 0) {
            return contacts.get(0);
        } else {
            throw new ContactException(ANSI_RED + "No contacts with ID " + id + " were found.");
        }
    }

    public List<Contact> findByName(String name) {
        List<Contact> contacts = list.stream()
                .filter(x -> Objects.equals(x.getName(), name))
                .collect(Collectors.toList());

        if (contacts.size() > 0) {
            return contacts;
        } else {
            throw new ContactException(ANSI_RED + "No contacts with NAME " + name + "were found.");
        }
    }

    public void addContact(Contact contact) {
        List<Contact> contacts = list.stream()
                .filter(x -> Objects.equals(x.getId(), contact.getId()))
                .collect(Collectors.toList());

        if (contacts.size() > 0) {
            throw new ContactException(ANSI_RED + "Contact with ID " + contact.getId() + " already exists. Nothing done.");
        } else {
            list.add(contact);
        }

    }

    public void removeContact(int id) {
        Object toRemove = findByID(id);
        if (toRemove != Optional.empty()) {
            list = list.stream()
                    .filter(x -> x.getId() != id)
                    .collect(Collectors.toList());
        }
    }
}
