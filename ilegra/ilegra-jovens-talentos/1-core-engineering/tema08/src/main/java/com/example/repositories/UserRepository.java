package com.example.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.entities.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class UserRepository implements JsonRepository<User> {

    private static final String USER_JSON = "db/user.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    @Override
    public List<User> getAll() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(USER_JSON));
        String readerString = reader.readLine();
        List<User> emptyList = new ArrayList<>();

        if (!Objects.isNull(readerString)) {
            User[] userList = OBJECT_MAPPER.readValue(Paths.get(USER_JSON).toFile(), User[].class);
            return new ArrayList<>(Arrays.stream(userList).toList());
        } else return emptyList;
    }

    @Override
    public User save(User var1) throws IOException {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        List<User> userList = getAll();
        userList.add(var1);
        OBJECT_MAPPER.writeValue(new File(USER_JSON), userList);
        return var1;
    }

    @Override
    public void overwrite(List<User> var1) throws IOException {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        OBJECT_MAPPER.writeValue(new File(USER_JSON), var1);
    }

    @Override
    public Optional<User> findById(long varID) throws IOException {
        return getAll()
                .stream()
                .parallel()
                .filter(x -> Objects.equals(varID, x.getUserID()))
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
        List<User> userList = getAll();
        userList.removeIf(x -> Objects.equals(varID, x.getUserID()));
        overwrite(userList);
    }

    @Override
    public void deleteAll() throws IOException {
        overwrite(Collections.emptyList());
    }
}
