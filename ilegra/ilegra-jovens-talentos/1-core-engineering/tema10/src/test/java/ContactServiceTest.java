import com.example.entities.Contact;
import com.example.repositories.ContactRepository;
import com.example.services.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactServiceTest {

    private static final Contact KIARA =
            new Contact(1L, "Takanashi Kiara", "kiara@kfp.com", "690301549");

    private static final Contact CALLI =
            new Contact(2L, "Calliope Mori", "mori@underworld.com", "4444444444");

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAll() throws SQLException {
        when(contactRepository.findAll()).thenReturn(List.of(KIARA, CALLI));
        contactService.findAll();
        verify(contactRepository).findAll();
        assertEquals(List.of(KIARA, CALLI), contactService.findAll());
    }

    @Test
    public void shouldSaveContact() throws SQLException {
        when(contactRepository.save(KIARA)).thenReturn(KIARA);
        Contact c = contactService.save(KIARA);
        verify(contactRepository).save(c);
        assertEquals(KIARA, c);
    }

    @Test
    public void shouldDeleteByID() throws SQLException {
        when(contactRepository.deleteById(1L)).thenReturn(true);
        boolean b = contactService.deleteById(1L);
        verify(contactRepository).deleteById(1L);
        assertTrue(b);
    }

    @Test
    public void shouldNotDeleteByID() throws SQLException {
        when(contactRepository.deleteById(19L)).thenReturn(false);
        boolean b = contactService.deleteById(19L);
        verify(contactRepository).deleteById(19L);
        assertFalse(b);
    }

    @Test
    public void shouldFindByName() throws SQLException {
        when(contactRepository.findByName("Calliope Mori")).thenReturn(List.of(CALLI));
        List<Contact> list = contactService.findByName("Calliope Mori");
        verify(contactRepository).findByName("Calliope Mori");
        assertEquals(contactService.findByName("Calliope Mori"), list);
    }

    @Test
    public void shouldNotFindAnyNames() throws SQLException {
        when(contactRepository.findByName("NO MATCH")).thenReturn(List.of());
        List<Contact> list = contactService.findByName("NO MATCH");
        verify(contactRepository).findByName("NO MATCH");
        assertEquals(contactService.findByName("NO MATCH"), list);
    }

    @Test
    public void shouldFindById() throws SQLException {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(KIARA));
        Contact contact = contactService.findById(1L).get();
        verify(contactRepository).findById(1L);
        assertEquals(contactService.findById(1L).get(), contact);
    }

    @Test
    public void shouldNotFindAnyById() throws SQLException {
        when(contactRepository.findById(397L)).thenReturn(Optional.empty());
        Optional<Contact> contact = contactService.findById(397L);
        verify(contactRepository).findById(397L);
        assertEquals(contactService.findById(397L), contact);
    }

    @Test
    public void shouldSortByIdAsc() throws SQLException {
        Contact gura = new Contact(3L, "Gura", "gura@atlantis.com", "404058950");
        Contact ina = new Contact(7L, "Ina", "therealina@tako.com", "58694930");
        List<Contact> list = new ArrayList<>(List.of(KIARA, CALLI, ina, gura));

        List<Contact> sortedList = list.stream()
                .parallel()
                .sorted(Comparator.comparingLong(Contact::getId))
                .collect(Collectors.toList());

        when(contactRepository.sortByIdAsc()).thenReturn(List.of(KIARA, CALLI, gura, ina));
        List<Contact> realSort = contactService.sortByIdAsc();
        verify(contactRepository).sortByIdAsc();
        assertEquals(sortedList, realSort);
    }

    @Test
    public void shouldSortByIdDesc() throws SQLException {
        Contact gura = new Contact(3L, "Gura", "gura@atlantis.com", "404058950");
        Contact ina = new Contact(7L, "Ina", "therealina@tako.com", "58694930");
        List<Contact> list = new ArrayList<>(List.of(KIARA, CALLI, ina, gura));

        List<Contact> sortedList = list.stream()
                .parallel()
                .sorted(Comparator.comparingLong(Contact::getId).reversed())
                .collect(Collectors.toList());

        when(contactRepository.sortByIdDesc()).thenReturn(List.of(ina, gura, CALLI, KIARA));
        List<Contact> realSort = contactService.sortByIdDesc();
        verify(contactRepository).sortByIdDesc();
        assertEquals(sortedList, realSort);
    }

    @Test
    public void shouldSortByNameAsc() throws SQLException {
        Contact gura = new Contact(3L, "Gura", "gura@atlantis.com", "404058950");
        Contact ina = new Contact(99L, "Ina", "therealina@tako.com", "58694930");
        List<Contact> list = new ArrayList<>(List.of(KIARA, CALLI, ina, gura));

        List<Contact> sortedList = list.stream()
                .sorted(Comparator.comparing(Contact::getName))
                .collect(Collectors.toList());

        when(contactRepository.sortByNameAsc()).thenReturn(List.of(CALLI, gura, ina, KIARA));
        List<Contact> realSort = contactService.sortByNameAsc();
        verify(contactRepository).sortByNameAsc();
        assertEquals(sortedList, realSort);
    }

    @Test
    public void shouldSortByNameDesc() throws SQLException {
        Contact gura = new Contact(3L, "Gura", "gura@atlantis.com", "404058950");
        Contact ina = new Contact(99L, "Ina", "therealina@tako.com", "58694930");
        List<Contact> list = new ArrayList<>(List.of(KIARA, CALLI, ina, gura));

        List<Contact> sortedList = list.stream()
                .sorted(Comparator.comparing(Contact::getName).reversed())
                .collect(Collectors.toList());

        when(contactRepository.sortByNameDesc()).thenReturn(List.of(KIARA, ina, gura, CALLI));
        List<Contact> realSort = contactService.sortByNameDesc();
        verify(contactRepository).sortByNameDesc();
        assertEquals(sortedList, realSort);
    }
}
