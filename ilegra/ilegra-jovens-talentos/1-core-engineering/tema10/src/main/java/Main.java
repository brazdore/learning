import com.example.entities.Contact;
import com.example.services.ContactService;

import java.sql.SQLException;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) throws SQLException {

        ContactService contactService = new ContactService();

        System.out.println(ANSI_YELLOW + contactService.sortByNameAsc());
        System.out.println(ANSI_PURPLE + contactService.sortByNameDesc());
        System.out.println(ANSI_YELLOW + contactService.sortByIdAsc());
        System.out.println(ANSI_PURPLE + contactService.sortByIdDesc());

        System.out.println(ANSI_BLUE + contactService.findAll());

        System.out.println(ANSI_GREEN + contactService.findById(1L));
        System.out.println(ANSI_GREEN + contactService.findById(70L));

        System.out.println(ANSI_CYAN + contactService.save(new Contact("Polka", "polka@circus.jp", "405696040")));

        System.out.println(ANSI_RED + contactService.findByName("Polka"));
        System.out.println(ANSI_RED + contactService.findByName("NO MATCH"));

        System.out.println(contactService.deleteById(1L));
        System.out.println(contactService.deleteById(70L));

        System.out.print(ANSI_RESET);
    }
}
