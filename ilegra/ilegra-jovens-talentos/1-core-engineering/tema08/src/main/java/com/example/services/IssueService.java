package com.example.services;

import com.example.entities.Issue;
import com.example.exceptions.IssueException;
import com.example.repositories.IssueRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class IssueService {

    private static long DAY_IN_MS = 86400000;
    IssueRepository issueRepository = new IssueRepository();


    public IssueService() throws IOException {
    }

    public List<Issue> getAll() throws IOException {
        return issueRepository.getAll();
    }

    public Issue save(Issue issue) throws IOException {
        return issueRepository.save(issue);
    }

    public List<Issue> saveList(List<Issue> issues) {
        issues.forEach(x -> {
            try {
                issueRepository.save(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return issues;
    }

    public boolean existsByID(long issueID) throws IOException {
        return issueRepository.existsById(issueID);
    }

    public Optional<Issue> findIssueByID(long issueID) throws IOException {
        return issueRepository.findById(issueID);
    }

    public Optional<Issue> findIssueByBookID(long bookID) throws IOException {
        return getAll().stream()
                .parallel()
                .filter(x -> Objects.equals(x.getBookID(), bookID))
                .findFirst();
    }

    public void extendReturnByIssueID(long issueID) throws IOException {
        if (!existsByID(issueID)) {
            throw new IssueException("Issue ID [" + issueID + "] could not be found.");
        } else {
            Issue issue = issueRepository.findById(issueID).get();
            issue.extendIssue();
            issueRepository.deleteById(issueID);
            issueRepository.save(issue);
        }
    }

    public void extendReturnByBookID(long bookID) throws IOException {
        if (findIssueByBookID(bookID).isEmpty()) {
            throw new IssueException("No issues were found for Book ID [" + bookID + "].");
        } else {
            Issue issue = issueRepository.findById(bookID).get();
            issue.extendIssue();
            issueRepository.deleteById(issue.getIssueID());
            issueRepository.save(issue);
        }
    }

    public void closeIssue(long issueID) throws IOException {
        if (!existsByID(issueID)) {
            throw new IssueException("Issue ID [" + issueID + "] could not be found.");
        } else {
            Issue issue = issueRepository.findById(issueID).get();
            issue.closeIssue();
            issueRepository.deleteById(issueID);
            issueRepository.save(issue);
        }
    }

    public List<Issue> getAllOpenIssues() throws IOException {
        return getAll().stream()
                .parallel()
                .filter(Issue::isOpen)
                .collect(Collectors.toList());
    }

    public void deleteAll() throws IOException {
        issueRepository.deleteAll();
    }

    public long count() throws IOException {
        return issueRepository.count();
    }

    public void changeReturnDate(long issueID, long returnDate) throws IOException {
        if (!existsByID(issueID)) {
            throw new IssueException("No issues were found with ID [" + issueID + "].");
        } else {
            Optional<Issue> issue = issueRepository.findById(issueID);
            issue.get().setReturnAt(returnDate);
            issueRepository.deleteById(issueID);
            issueRepository.save(issue.get());
        }
    }

    public boolean canLoanDate(long userID) throws IOException {
        return Objects.equals(getAll().stream()
                .parallel()
                .filter(x -> Objects.equals(userID, x.getUserID()))
                .filter(x -> System.currentTimeMillis() > x.getReturnAt())
                .count(), 0L);
    }

    public int getCurrentlyLateAmountByUserID(long userID) throws IOException {
        return (int) getAll().stream()
                .filter(x -> Objects.equals(x.getUserID(), userID))
                .filter(Issue::isOpen)
                .count();
    }

    public int daysToFine(long bookID) throws IOException {
        long daysLate = 0L;
        if (findIssueByBookID(bookID).isPresent() && findIssueByBookID(bookID).get().isOpen()) {
            Issue issue = findIssueByBookID(bookID).get();
            daysLate = (System.currentTimeMillis() - issue.getReturnAt()) / DAY_IN_MS;
        }
        return (int) daysLate;
    }
}
