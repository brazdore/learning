package com.example.UI;

import com.google.common.collect.Multimap;
import com.example.entities.Book;
import com.example.entities.Issue;
import com.example.entities.User;
import com.example.services.LoanService;
import com.example.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UI {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private final LoanService loanService = new LoanService();
    private final UserService userService = new UserService();

    public UI() throws IOException {
    }

    public void printAllLoanedBooksSummary() throws IOException {
        Multimap<User, Book> multimap = loanService.allLoanedBooksSummary();
        Set<User> users = multimap.keySet();

        for (User user : users) {
            System.out.println(ANSI_BLUE + "Name: " + user.getName() + " | ID: " + user.getUserID());
            System.out.println(ANSI_GREEN + "Books: IDs: " + multimap.get(user).stream().map(Book::getBookID).collect(Collectors.toList())
                    + " | Titles: " + multimap.get(user).stream().map(Book::getTitle).collect(Collectors.toList()));
        }
    }

    public void printAllExpiredIssuesSummary() throws IOException {
        Multimap<User, Issue> multimap = loanService.allLateIssues();
        Set<User> users = multimap.keySet();

        for (User user : users) {
            System.out.println(ANSI_BLUE + "Name: " + user.getName() + " | ID: " + user.getUserID());
            System.out.println(ANSI_YELLOW + "Expired Issues: IDs: " + multimap.get(user).stream().map(Issue::getIssueID).collect(Collectors.toList())
                    + " | Expired by Days: " + multimap.get(user).stream().map(x -> (System.currentTimeMillis() - x.getReturnAt()) / 86400000)
                    .collect(Collectors.toList()));
        }
    }

    public void printAllTopUsers(int i) throws IOException {
        List<User> users = userService.topUsers(i);

        for (User user : users) {
            System.out.println(ANSI_BLUE + "Name: " + user.getName() + " [ID: " + user.getUserID()
                    + "]" + ANSI_YELLOW + " AMOUNT RENTED: " + user.getAllRentedAmount());
        }
    }

    public void resetColor() {
        System.out.println(ANSI_RESET);
    }
}
