import entities.Contact;
import entities.ContactException;
import entities.ContactService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ContactTest {

    ContactService contactService;

    @BeforeEach
    void init() {
        contactService = new ContactService();

        Contact pekora = new Contact(1, "Pekora", null, null);
        Contact kiara = new Contact(2, "Kiara", null, null);

        contactService.addContact(pekora);
        contactService.addContact(kiara);
    }

    @Test
    public void shouldAddContact() {
        Contact gura = new Contact(5, "Gura", null, null);
        contactService.addContact(gura);
        Assertions.assertEquals(contactService.getList().get(2), gura);
    }

    @Test
    public void shouldThrowExceptionAtAddingExistingID() {
        Contact gura = new Contact(1, "Gura", null, null);
        Assertions.assertThrows
                (ContactException.class,
                        () -> contactService.addContact(gura), "Contact should not be registered.");
    }

    @Test
    public void shouldDeleteContact() {
        contactService.removeContact(1);
        Assertions.assertThrows
                (ContactException.class,
                        () -> contactService.removeContact(1), "ID should not be found.");
    }

    @Test
    public void shouldThrowExceptionAtDeletingNonExistingContact() {
        Assertions.assertThrows
                (ContactException.class,
                        () -> contactService.removeContact(5), "Contact should not be deleted.");

    }

    @Test
    public void shouldReturnContactList() {
        List<Contact> contactListAll = contactService.listAll();
        Assertions.assertEquals(contactListAll.size(), 2);
    }

    @Test
    public void shouldThrowExceptionWhenNoContacts() {
        contactService.removeContact(1);
        contactService.removeContact(2);

        Assertions.assertThrows
                (ContactException.class,
                        () -> contactService.listAll(), "No contacts should throw exception.");
    }

    @Test
    public void shouldFindByID() {
        Contact pekora = new Contact(1, "Pekora", null, null);
        Assertions.assertEquals(contactService.findByID(1), pekora);
    }

    @Test
    public void shouldThrowExceptionAtTryingToFindNonExistingID() {
        Assertions.assertThrows
                (ContactException.class,
                        () -> contactService.findByID(5), "Should throw exception at invalid ID.");
    }

    @Test
    public void shouldFindByName() {
        Contact kiara = new Contact(2, "Kiara", null, null);
        List<Contact> kiaraList = new ArrayList<>();
        kiaraList.add(kiara);

        Assertions.assertEquals(contactService.findByName("Kiara"), kiaraList);
    }

    @Test
    public void shouldThrowExceptionAtSearchingInvalidName() {
        Assertions.assertThrows
                (ContactException.class,
                        () -> contactService.findByName("Ninomae Ina'nis"), "Should throw exception at invalid name.");
    }
}
