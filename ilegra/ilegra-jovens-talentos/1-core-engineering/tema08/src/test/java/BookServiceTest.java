import com.example.entities.Book;
import com.example.exceptions.BookException;
import com.example.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.List;

public class BookServiceTest {

    BookService bookService = new BookService();
    Book tako;

    @BeforeEach
    void init() throws IOException {
        bookService.deleteAll();
        tako = new Book(1L, "How to Tako", "Ninomae Ina'nis", true);
        Book cook1 = new Book(2L, "Haachama Cooking", "Haachama", false);
        Book a = new Book(3L, "A", "Gawr Gura", true);
        Book cook2 = new Book(4L, "Haachama Cooking", "Haachama", true);
        bookService.saveList(List.of(tako, cook1, a, cook2));
    }

    @org.junit.jupiter.api.Test
    public void shouldAddBookToDB() throws IOException {
        Assertions.assertEquals(4, bookService.getAll().size());
        Book book = bookService.save(new Book(5L, "Ground Pounding: The Fine Art", "Amelia Watson", true));
        Assertions.assertEquals(bookService.getAll().get(4), book);
        Assertions.assertEquals(5, bookService.getAll().size());
    }

    @org.junit.jupiter.api.Test
    public void shouldAddBookListToDB() throws IOException {
        Assertions.assertEquals(4, bookService.getAll().size());
        List<Book> books = bookService.saveList(List.of(new Book(5L, "Ground Pounding: The Fine Art", "Amelia Watson", true)
                , new Book(6L, "HA ^ HA v HA ^ HA v", "Usada Pekora", false)));
        List<Book> lastTwoBooks = bookService.getAll().subList(4, 6);
        Assertions.assertEquals(books, lastTwoBooks);
        Assertions.assertEquals(6, bookService.getAll().size());
    }

    @org.junit.jupiter.api.Test
    public void shouldFindBookByID() throws IOException {
        Assertions.assertEquals(bookService.findBookByID(1L).get(), tako);
    }

    @org.junit.jupiter.api.Test
    public void shouldNotFindUserByID() throws IOException {
        Assertions.assertTrue(bookService.findBookByID(15L).isEmpty());
    }

    @org.junit.jupiter.api.Test
    public void shouldDeleteBookRegister() throws IOException {
        Assertions.assertEquals(4, bookService.getAll().size());
        Assertions.assertEquals(bookService.getAll().get(0), tako);
        bookService.deleteBookRegister(1L);
        Assertions.assertEquals(3, bookService.getAll().size());
        Assertions.assertNotEquals(bookService.getAll().get(0), tako);
    }

    @org.junit.jupiter.api.Test
    public void shouldThrowWhenIlegalDeleting() {
        Assertions.assertThrows(BookException.class, () ->
                bookService.deleteBookRegister(2L), "Should NOT delete book when loaned.");
        Assertions.assertThrows(BookException.class, () ->
                bookService.deleteBookRegister(15L), "Should NOT delete book when no ID matched.");
    }

    @org.junit.jupiter.api.Test
    public void shouldFindAllBooksByTitle() throws IOException {
        List<Book> cooking = bookService.findBookByTitle("Haachama Cooking");
        Assertions.assertEquals(2, cooking.size());
        Assertions.assertEquals(bookService.getAll().get(1).getBookID(), 2L);
        Assertions.assertEquals(bookService.getAll().get(3).getBookID(), 4L);
        List<Book> noMatch = bookService.findBookByTitle("No Match");
        Assertions.assertEquals(noMatch, List.of());
    }

    @org.junit.jupiter.api.Test
    public void shouldFindAllBooksByAuthor() throws IOException {
        List<Book> cooking = bookService.findBookByAuthor("Haachama");
        Assertions.assertEquals(2, cooking.size());
        Assertions.assertEquals(bookService.getAll().get(1).getBookID(), 2L);
        Assertions.assertEquals(bookService.getAll().get(3).getBookID(), 4L);
        List<Book> noMatch = bookService.findBookByAuthor("No Match");
        Assertions.assertEquals(noMatch, List.of());
    }

    @org.junit.jupiter.api.Test
    public void shouldSetStockTrue() throws IOException {
        Assertions.assertFalse(bookService.findBookByID(2L).get().isInStock());
        bookService.setBookStockTrue(2L);
        Assertions.assertTrue(bookService.findBookByID(2L).get().isInStock());
    }

    @org.junit.jupiter.api.Test
    public void shouldSetStockFalse() throws IOException {
        Assertions.assertTrue(bookService.findBookByID(1L).get().isInStock());
        bookService.setBookStockFalse(1L);
        Assertions.assertFalse(bookService.findBookByID(1L).get().isInStock());
    }
}
