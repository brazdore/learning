package com.example.services;

import com.example.entities.Book;
import com.example.entities.Issue;
import com.example.entities.User;
import com.example.exceptions.BookException;
import com.example.exceptions.FineException;
import com.example.exceptions.LoanException;
import com.example.exceptions.UserException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoanService {

    private static final int MAX_BOOK_AMOUNT = 5;
    private static final double FINE_PER_DAY = 5.0;

    UserService userService = new UserService();
    BookService bookService = new BookService();
    IssueService issueService = new IssueService();

    public LoanService() throws IOException {
    }

    public void issueBook(long userID, long bookID) throws IOException {
        if (!bookService.isBookAvaliable(bookID)) {
            throw new BookException("Book with ID [" + bookID + "] should not be on stock.");
        } else if (!userService.existsByID(userID)) {
            throw new UserException("There is no user with ID " + userID + ".");
        } else if (userService.canLoanAmount(userID, 1, MAX_BOOK_AMOUNT)
                && issueService.canLoanDate(userID)) {
            long issueID = issueService.count() + 1;
            bookService.setBookStockFalse(bookID);
            userService.increaseHoldingAndRented(userID, 1);
            issueService.save(new Issue(issueID, userID, bookID));
        } else throw new LoanException("Cannot exceed maximum loan amount of " + MAX_BOOK_AMOUNT +
                " OR cannot loan due to late return. You currently hold " + userService.getHoldingAmount(userID)
                + " books AND you currently have " + issueService.getCurrentlyLateAmountByUserID(userID) + " late returns.");
    }

    public void issueBookList(long userID, List<Long> bookIDList) throws IOException {
        for (Long bookID : bookIDList) {
            if (!bookService.isBookAvaliable(bookID)) {
                throw new BookException("Book with ID [" + bookID + "] should not be on stock.");
            }
        }
        if (!userService.existsByID(userID)) {
            throw new UserException("There is no user with ID " + userID + ".");
        } else if (userService.canLoanAmount(userID, bookIDList.size(), MAX_BOOK_AMOUNT)
                && issueService.canLoanDate(userID)) {
            for (Long bookIDAgain : bookIDList) {
                long issueID = issueService.count() + 1;
                bookService.setBookStockFalse(bookIDAgain);
                userService.increaseHoldingAndRented(userID, 1);
                issueService.save(new Issue(issueID, userID, bookIDAgain));
            }
        } else throw new LoanException("Cannot exceed maximum loan amount of " + MAX_BOOK_AMOUNT +
                " OR cannot loan due to late return. You currently hold " + userService.getHoldingAmount(userID)
                + " books AND you currently have " + issueService.getCurrentlyLateAmountByUserID(userID) + " late returns." +
                " You were trying to loan " + bookIDList.size() + " books.");
    }

    public void returnBook(long bookID) throws IOException {
        int daysToFine = Math.max(issueService.daysToFine(bookID), 0);
        if (!bookService.existsByID(bookID)) {
            throw new BookException("There is no book with ID " + bookID + ".");
        } else {
            Issue issue = issueService.findIssueByBookID(bookID).get();
            issueService.closeIssue(issue.getIssueID());
            userService.decreaseHolding(issue.getUserID(), 1);
            bookService.setBookStockTrue(bookID);
        }
        if (!Objects.equals(daysToFine, 0)) {
            throw new FineException("You are late by " + daysToFine + " days. The fine is $" +
                    FINE_PER_DAY + " per late day. Your fine is $" + daysToFine * FINE_PER_DAY + ".");
        }
    }

    public Multimap<User, Book> allLoanedBooksSummary() throws IOException {
        List<Issue> openIssues = issueService.getAllOpenIssues();
        Multimap<User, Book> multiMap = ArrayListMultimap.create();

        for (Issue issue : openIssues) {
            multiMap.put(userService.findUserByID(issue.getUserID()).get(),
                    bookService.findBookByID(issue.getBookID()).get());
        }
        return multiMap;
    }

    public Multimap<User, Issue> allLateIssues() throws IOException {
        List<Issue> openIssues = issueService.getAllOpenIssues();
        Multimap<User, Issue> multiMap = ArrayListMultimap.create();

        for (Issue issue : openIssues) {
            if (issue.getReturnAt() < System.currentTimeMillis()) {
                multiMap.put(userService.findUserByID(issue.getUserID()).get(),
                        issueService.findIssueByID(issue.getIssueID()).get());
            }
        }
        return multiMap;
    }
}