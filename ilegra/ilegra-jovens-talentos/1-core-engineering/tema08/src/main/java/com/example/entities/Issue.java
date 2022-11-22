package com.example.entities;

import java.util.Objects;

public class Issue {

    private long issueID;
    private long userID;
    private long bookID;
    private long rentedAt;
    private long returnAt;
    private boolean open;

    public Issue() {
    }

    public Issue(long issueID, long userID, long bookID) {
        this.issueID = issueID;
        this.userID = userID;
        this.bookID = bookID;
        this.rentedAt = System.currentTimeMillis();
        this.returnAt = rentedAt + 604800000;
        this.open = true;
    }

    public long getIssueID() {
        return issueID;
    }

    public long getUserID() {
        return userID;
    }

    public long getBookID() {
        return bookID;
    }

    public long getRentedAt() {
        return rentedAt;
    }

    public long getReturnAt() {
        return returnAt;
    }

    public void setReturnAt(long returnAt) {
        this.returnAt = returnAt;
    }

    public void extendIssue() {
        returnAt += 604800000;
    }

    public boolean isOpen() {
        return open;
    }

    public void closeIssue() {
        this.open = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return issueID == issue.issueID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueID);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueID=" + issueID +
                ", userID=" + userID +
                ", bookID=" + bookID +
                ", rentedAt=" + rentedAt +
                ", returnAt=" + returnAt +
                '}';
    }
}
