package com.example.services;

import com.example.entities.Book;
import com.example.exceptions.BookException;
import com.example.repositories.BookRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {

    private final BookRepository bookRepository = new BookRepository();

    public BookService() {
    }

    public List<Book> getAll() throws IOException {
        return bookRepository.getAll();
    }

    public Book save(Book book) throws IOException {
        return bookRepository.save(book);
    }

    public List<Book> saveList(List<Book> books) {
        books.forEach(x -> {
            try {
                bookRepository.save(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return books;
    }

    public boolean existsByID(long bookID) throws IOException {
        return bookRepository.existsById(bookID);
    }

    public void deleteBookRegister(long id) throws IOException {
        if (bookRepository.findById(id).isPresent() && bookRepository.findById(id).get().isInStock()) {
            bookRepository.deleteById(id);
        } else throw new BookException("ID [" + id + "] could not be found OR book is currently loaned.");
    }

    public List<Book> findBookByTitle(String title) throws IOException {
        return getAll().stream()
                .parallel()
                .filter(x -> Objects.equals(x.getTitle(), title))
                .collect(Collectors.toList());
    }

    public List<Book> findBookByAuthor(String author) throws IOException {
        return getAll().stream()
                .parallel()
                .filter(x -> Objects.equals(x.getAuthor(), author))
                .collect(Collectors.toList());
    }

    public Optional<Book> findBookByID(long id) throws IOException {
        return bookRepository.findById(id);
    }

    public void setBookStockFalse(long id) throws IOException {
        if (!existsByID(id)) {
            throw new BookException("No books were found with ID [" + id + "].");
        } else {
            Book book = bookRepository.findById(id).get();
            bookRepository.deleteById(id);
            book.setStockFalse();
            bookRepository.save(book);
        }
    }

    public void setBookStockTrue(long id) throws IOException {
        if (!existsByID(id)) {
            throw new BookException("No books were found with ID [" + id + "].");
        } else {
            Book book = bookRepository.findById(id).get();
            bookRepository.deleteById(id);
            book.setStockTrue();
            bookRepository.save(book);
        }
    }

    public boolean isBookAvaliable(long id) throws IOException {
        if (!existsByID(id)) {
            throw new BookException("No books were found with ID [" + id + "].");
        } else return findBookByID(id).get().isInStock();
    }

    public void deleteAll() throws IOException {
        bookRepository.deleteAll();
    }
}
