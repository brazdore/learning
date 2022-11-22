import com.example.UI.UI;
import com.example.entities.Book;
import com.example.entities.User;
import com.example.services.BookService;
import com.example.services.IssueService;
import com.example.services.LoanService;
import com.example.services.UserService;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        UI UI = new UI();
        LoanService loanService = new LoanService();
        BookService bookService = new BookService();
        IssueService issueService = new IssueService();
        UserService userService = new UserService();
        long NOW_IN_MS = System.currentTimeMillis();
        long WEEK_IN_MS = 604800000;

        userService.deleteAll();
        bookService.deleteAll();
        issueService.deleteAll();

        Book book1 = new Book(1L, "The Art of War", "Usada Pekora", true);
        Book book2 = new Book(2L, "Haachama Cooking 101", "Haachama", true);
        Book book3 = new Book(3L, "A", "Gawr Gura", true);
        Book book4 = new Book(4L, "How to Tako", "Ninomae Ina'nis", true);
        Book book5 = new Book(5L, "How to Tako", "Ninomae Ina'nis", true);

        bookService.saveList(List.of(book1, book2, book3, book4, book5));

        User ame = new User(1L, "Amelia");
        User ina = new User(2L, "Ina");
        User kiara = new User(3L, "Kiara");
        User nenechi = new User(4L, "Nenechi");
        User calli = new User(5L, "Calli");

        userService.saveList(List.of(ame, ina, kiara, nenechi, calli));

        loanService.issueBookList(1L, List.of(1L, 2L, 3L));
        loanService.issueBook(3L, 4L);
        loanService.issueBook(4L, 5L);

        issueService.changeReturnDate(1L, NOW_IN_MS - WEEK_IN_MS);
        issueService.changeReturnDate(2L, NOW_IN_MS + WEEK_IN_MS);
        issueService.changeReturnDate(3L, NOW_IN_MS - WEEK_IN_MS);
        issueService.changeReturnDate(4L, NOW_IN_MS - (WEEK_IN_MS / 2));
        issueService.changeReturnDate(5L, NOW_IN_MS + (WEEK_IN_MS / 7));

        UI.printAllExpiredIssuesSummary();
        UI.resetColor();

        UI.printAllLoanedBooksSummary();
        UI.resetColor();

        UI.printAllTopUsers(10);
        UI.resetColor();
    }
}