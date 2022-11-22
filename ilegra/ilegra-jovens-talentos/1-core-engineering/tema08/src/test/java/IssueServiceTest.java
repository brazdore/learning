import com.example.entities.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import com.example.services.IssueService;

import java.io.IOException;
import java.util.List;

public class IssueServiceTest {

    IssueService issueService;
    Issue issue1;

    {
        try {
            issueService = new IssueService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void init() throws IOException {
        issueService.deleteAll();
        issue1 = new Issue(1L, 1L, 1L);
        Issue issue2 = new Issue(2L, 2L, 2L);
        Issue issue3 = new Issue(3L, 3L, 3L);

        issueService.saveList(List.of(issue1, issue2, issue3));
    }

    @org.junit.jupiter.api.Test
    public void shouldAddIssueToDB() throws IOException {
        Assertions.assertEquals(3, issueService.getAll().size());
        Issue issue = issueService.save(new Issue(4L, 4L, 4L));
        Assertions.assertEquals(issueService.getAll().get(3), issue);
        Assertions.assertEquals(4, issueService.getAll().size());
    }

    @org.junit.jupiter.api.Test
    public void shouldAddIssueListToDB() throws IOException {
        Assertions.assertEquals(3, issueService.getAll().size());
        List<Issue> issues = issueService.saveList(List.of(new Issue(4L, 4L, 4L)
                , new Issue(5L, 5L, 5L)));
        List<Issue> lastTwoIssues = issueService.getAll().subList(3, 5);
        Assertions.assertEquals(issues, lastTwoIssues);
        Assertions.assertEquals(5, issueService.getAll().size());
    }

    @org.junit.jupiter.api.Test
    public void shouldFindIssueByID() throws IOException {
        Assertions.assertEquals(issueService.findIssueByID(1L).get(), issue1);
    }

    @org.junit.jupiter.api.Test
    public void shouldFindIssueByBookID() throws IOException {
        Assertions.assertEquals(issueService.findIssueByBookID(1L).get(), issue1);
    }

    @org.junit.jupiter.api.Test
    public void shouldNotFindIssueByID() throws IOException {
        Assertions.assertTrue(issueService.findIssueByID(15L).isEmpty());
    }

    @org.junit.jupiter.api.Test
    public void shouldNotFindIssueByBookID() throws IOException {
        Assertions.assertTrue(issueService.findIssueByBookID(15L).isEmpty());
    }

    @org.junit.jupiter.api.Test
    public void shouldExtentReturnByIssueID() throws IOException {
        long returnBefore = issueService.findIssueByID(1L).get().getReturnAt();
        issueService.extendReturnByIssueID(1L);
        Assertions.assertTrue(issueService.findIssueByID(1L).get().getReturnAt() > returnBefore);
    }

    @org.junit.jupiter.api.Test
    public void shouldExtentReturnByBookID() throws IOException {
        long returnBefore = issueService.findIssueByID(1L).get().getReturnAt();
        issueService.extendReturnByBookID(1L);
        Assertions.assertTrue(issueService.findIssueByID(1L).get().getReturnAt() > returnBefore);
    }

    @org.junit.jupiter.api.Test
    public void shouldCloseIssue() throws IOException {
        Assertions.assertTrue(issueService.findIssueByID(1L).get().isOpen());
        issueService.closeIssue(1L);
        Assertions.assertFalse(issueService.findIssueByID(1L).get().isOpen());
    }

    @org.junit.jupiter.api.Test
    public void shouldGetAllOpenIssues() throws IOException {
        Assertions.assertEquals(issueService.getAllOpenIssues().size(), 3);
        issueService.closeIssue(1L);
        Assertions.assertEquals(issueService.getAllOpenIssues().size(), 2);
    }
}
