package com.example.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.entities.Issue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class IssueRepository implements JsonRepository<Issue> {

    private static final String ISSUE_JSON = "db/issue.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public List<Issue> getAll() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ISSUE_JSON));
        String readerString = reader.readLine();
        List<Issue> emptyList = new ArrayList<>();

        if (!Objects.isNull(readerString)) {
            Issue[] issueList = OBJECT_MAPPER.readValue(Paths.get(ISSUE_JSON).toFile(), Issue[].class);
            return new ArrayList<>(Arrays.stream(issueList).toList());
        } else return emptyList;
    }

    @Override
    public Issue save(Issue var1) throws IOException {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        List<Issue> issueList = getAll();
        issueList.add(var1);
        OBJECT_MAPPER.writeValue(new File(ISSUE_JSON), issueList);
        return var1;
    }

    @Override
    public void overwrite(List<Issue> var1) throws IOException {
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        OBJECT_MAPPER.writeValue(new File(ISSUE_JSON), var1);
    }

    @Override
    public Optional<Issue> findById(long varID) throws IOException {
        return getAll()
                .stream()
                .parallel()
                .filter(x -> Objects.equals(varID, x.getIssueID()))
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
        List<Issue> issueList = getAll();
        issueList.removeIf(x -> Objects.equals(varID, x.getIssueID()));
        overwrite(issueList);
    }

    @Override
    public void deleteAll() throws IOException {
        overwrite(Collections.emptyList());
    }
}
