package com.example.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.entities.Book;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class BookRepository implements JsonRepository<Book> {

    private static final String BOOK_JSON = "db/book.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public List<Book> getAll() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(BOOK_JSON));
        String readerString = reader.readLine();
        List<Book> emptyList = new ArrayList<>();

        if (!Objects.isNull(readerString)) {
            Book[] bookList = OBJECT_MAPPER.readValue(Paths.get(BOOK_JSON).toFile(), Book[].class);
            return new ArrayList<>(Arrays.stream(bookList).toList());
        } else return emptyList;
    }

    @Override
    public Book save(Book var1) throws IOException {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        List<Book> bookList = getAll();
        bookList.add(var1);
        OBJECT_MAPPER.writeValue(new File(BOOK_JSON), bookList);
        return var1;
    }

    @Override
    public void overwrite(List<Book> var1) throws IOException {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        OBJECT_MAPPER.writeValue(new File(BOOK_JSON), var1);
    }

    @Override
    public Optional<Book> findById(long varID) throws IOException {
        return getAll()
                .stream()
                .parallel()
                .filter(x -> Objects.equals(varID, x.getBookID()))
                .findFirst();
    }

    @Override
    public boolean existsById(long varID) throws IOException {
        return findById(varID).isPresent();
    }

    @Override
    public long count() throws IOException {
        return getAll().size();
    }

    @Override
    public void deleteById(long varID) throws IOException {
        List<Book> bookList = getAll();
        bookList.removeIf(x -> Objects.equals(varID, x.getBookID()));
        overwrite(bookList);
    }

    @Override
    public void deleteAll() throws IOException {
        overwrite(Collections.emptyList());
    }
}
