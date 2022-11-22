import com.example.entities.Book;
import com.example.entities.User;
import com.example.exceptions.BookException;
import com.example.exceptions.FineException;
import com.example.exceptions.LoanException;
import com.example.exceptions.UserException;
import com.example.services.BookService;
import com.example.services.IssueService;
import com.example.services.LoanService;
import com.example.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.List;

public class LoanServiceTest {

    LoanService loanService;
    UserService userService;
    BookService bookService;
    IssueService issueService;
    long WEEK_IN_MS = 604800000;

    {
        try {
            loanService = new LoanService();
            userService = new UserService();
            bookService = new BookService();
            issueService = new IssueService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeEach
    void init() throws IOException {
        userService.deleteAll();
        bookService.deleteAll();
        issueService.deleteAll();

        Book book1 = new Book(1L, "The Art of War", "Usada Pekora", true);
        Book book2 = new Book(2L, "Haachama Cooking 101", "Haachama", true);
        Book book3 = new Book(3L, "A", "Gawr Gura", true);

        bookService.saveList(List.of(book1, book2, book3));

        User ame = new User(1L, "Amelia");
        User ina = new User(2L, "Ina");
        User kiara = new User(3L, "Kiara");

        userService.saveList(List.of(ame, ina, kiara));
    }

    @org.junit.jupiter.api.Test
    public void shouldIssueBook() throws IOException {
        Assertions.assertTrue(issueService.getAll().isEmpty());
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 0);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 0);
        Assertions.assertEquals(userService.findUserByID(2L).get().getCurrentHolding(), 0);
        Assertions.assertEquals(userService.findUserByID(2L).get().getAllRentedAmount(), 0);
        Assertions.assertEquals(userService.findUserByID(3L).get().getCurrentHolding(), 0);
        Assertions.assertEquals(userService.findUserByID(3L).get().getAllRentedAmount(), 0);
        loanService.issueBook(1L, 1L);
        loanService.issueBook(1L, 2L);
        loanService.issueBook(2L, 3L);
        Assertions.assertEquals(issueService.getAll().size(), 3);
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 2);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 2);
        Assertions.assertEquals(userService.findUserByID(2L).get().getCurrentHolding(), 1);
        Assertions.assertEquals(userService.findUserByID(2L).get().getAllRentedAmount(), 1);
        Assertions.assertEquals(userService.findUserByID(3L).get().getCurrentHolding(), 0);
        Assertions.assertEquals(userService.findUserByID(3L).get().getAllRentedAmount(), 0);
    }

    @org.junit.jupiter.api.Test
    public void shouldThrowUserExWhenIDNotFound() {
        Assertions.assertThrows(UserException.class, () ->
                loanService.issueBook(9L, 1L), "Should throw when user ID not found.");
    }

    @org.junit.jupiter.api.Test
    public void shouldThrowLoanExWhenHoldingTooMany() throws IOException {
        userService.increaseHoldingAndRented(1L, 5);
        Assertions.assertThrows(LoanException.class, () ->
                loanService.issueBook(1L, 1L), "Should throw when user already has too many books.");
    }

    @org.junit.jupiter.api.Test
    public void shouldThrowLoanExWhenUserLate() throws IOException {
        loanService.issueBook(1L, 1L);
        issueService.changeReturnDate(1L, System.currentTimeMillis() - (WEEK_IN_MS * 2));
        Assertions.assertThrows(LoanException.class, () ->
                loanService.issueBook(1L, 2L), "Should throw when user is late.");
    }

    @org.junit.jupiter.api.Test
    public void shouldIssueBookList() throws IOException {
        Assertions.assertTrue(issueService.getAll().isEmpty());
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 0);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 0);
        loanService.issueBookList(1L, List.of(1L, 2L, 3L));
        Assertions.assertEquals(issueService.getAll().size(), 3);
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 3);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 3);
        Assertions.assertFalse(bookService.findBookByID(1L).get().isInStock());
        Assertions.assertFalse(bookService.findBookByID(2L).get().isInStock());
        Assertions.assertFalse(bookService.findBookByID(3L).get().isInStock());
    }

    @org.junit.jupiter.api.Test
    public void shouldThrowLoanExWhenUserLateList() throws IOException {
        loanService.issueBook(1L, 1L);
        issueService.changeReturnDate(1L, System.currentTimeMillis() - (WEEK_IN_MS * 2));
        Assertions.assertThrows(LoanException.class, () ->
                loanService.issueBookList(1L, List.of(2L, 3L)), "Should throw when user is late.");
    }

    @org.junit.jupiter.api.Test
    public void shouldThrowLoanExWhenHoldingTooManyList() throws IOException {
        userService.increaseHoldingAndRented(1L, 3);
        Assertions.assertThrows(LoanException.class, () ->
                loanService.issueBookList(1L, List.of(1L, 2L, 3L)), "Should throw when user already has too many books.");
    }

    @org.junit.jupiter.api.Test
    public void shouldReturnBook() throws IOException {
        Assertions.assertTrue(issueService.getAll().isEmpty());
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 0);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 0);
        Assertions.assertTrue(bookService.findBookByID(1L).get().isInStock());
        Assertions.assertTrue(bookService.findBookByID(2L).get().isInStock());
        loanService.issueBook(1L, 1L);
        loanService.issueBook(1L, 2L);
        Assertions.assertFalse(bookService.findBookByID(1L).get().isInStock());
        Assertions.assertFalse(bookService.findBookByID(2L).get().isInStock());
        Assertions.assertEquals(issueService.getAll().size(), 2);
        Assertions.assertTrue(issueService.getAll().get(0).isOpen());
        Assertions.assertTrue(issueService.getAll().get(1).isOpen());
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 2);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 2);
        loanService.returnBook(1L);
        Assertions.assertFalse(issueService.getAll().get(1).isOpen());
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 1);
        Assertions.assertEquals(userService.findUserByID(1L).get().getAllRentedAmount(), 2);
        loanService.returnBook(2L);
        Assertions.assertFalse(issueService.getAll().get(1).isOpen());
        Assertions.assertEquals(userService.findUserByID(1L).get().getCurrentHolding(), 0);
    }

    @org.junit.jupiter.api.Test
    public void shouldThrowWhenNoBookWithID() {
        Assertions.assertThrows(BookException.class, () ->
                loanService.returnBook(9L), "Should throw when no book ID match.");
    }

    @org.junit.jupiter.api.Test
    public void shouldThrowFineWhenReturningLate() throws IOException {
        loanService.issueBook(1L, 1L);
        issueService.changeReturnDate(1L, System.currentTimeMillis() - (WEEK_IN_MS * 2));
        Assertions.assertThrows(FineException.class, () ->
                loanService.returnBook(1L), "Should throw when returning late book.");
    }

    @org.junit.jupiter.api.Test
    public void shouldListAllCurrentlyLoanedBooks() throws IOException {
        Assertions.assertTrue(loanService.allLoanedBooksSummary().isEmpty());
        loanService.issueBook(1L, 1L);
        loanService.issueBook(2L, 2L);
        loanService.issueBook(2L, 3L);
        Assertions.assertEquals(loanService.allLoanedBooksSummary().size(), 3);
    }

    @org.junit.jupiter.api.Test
    public void shouldListAllCurrentlyLateIssues() throws IOException {
        Assertions.assertTrue(loanService.allLateIssues().isEmpty());
        loanService.issueBook(1L, 1L);
        loanService.issueBook(2L, 2L);
        loanService.issueBook(2L, 3L);
        issueService.changeReturnDate(1L, System.currentTimeMillis() - (WEEK_IN_MS * 2));
        issueService.changeReturnDate(2L, System.currentTimeMillis() - (WEEK_IN_MS * 2));
        issueService.changeReturnDate(3L, System.currentTimeMillis() - (WEEK_IN_MS * 2));
        Assertions.assertEquals(loanService.allLateIssues().size(), 3);
    }
}
