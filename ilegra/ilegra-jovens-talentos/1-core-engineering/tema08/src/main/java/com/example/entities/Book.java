package com.example.entities;

import java.util.Objects;

public class Book {

    private Long bookID;
    private String title;
    private String author;
    private boolean inStock;

    public Book() {
    }

    public Book(Long bookID, String title, String author, boolean inStock) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.inStock = inStock;
    }

    public Long getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setStockTrue() {
        inStock = true;
    }

    public void setStockFalse() {
        inStock = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookID.equals(book.bookID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookID);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + bookID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", inStock=" + inStock +
                '}';
    }
}
