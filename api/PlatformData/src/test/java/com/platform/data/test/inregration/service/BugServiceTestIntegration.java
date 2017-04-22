package com.platform.data.test.inregration.service;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/25/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "development")
@ContextConfiguration(locations = {"classpath:beans.xml", "classpath:persistence.xml"})
public class BugServiceTestIntegration {

    @Autowired
    private BugService bugService;

    @Autowired
    private ContextHolder contextHolder;


    @Test
    @WithMockCustomUser
    public void testCreateNewBug() {
        long activeBugsCount = bugService.findActiveBugs(new PageRequest(0, 20)).getTotalElements();
        Bug bug = new Bug("bug 1", "description", BugType.BUG, contextHolder.getCurrentTrader());
        bug = bugService.save(bug);

        Assert.assertNotNull(bug);
        Assert.assertNotNull(bug.getId());
        Assert.assertTrue(bugService.findActiveBugs(new PageRequest(0, 20)).getContent().size() > activeBugsCount);
    }


    @Test
    @WithMockCustomUser
    public void testResolve() {
        long activeBugsCount = bugService.findActiveBugs(new PageRequest(0, 20)).getTotalElements();
        long resolvedBugsCount = bugService.findResolvedBugs(new PageRequest(0, 20)).getTotalElements();

        Bug openedBug = new Bug("opened bug", "description", BugType.BUG, contextHolder.getCurrentTrader());
        Bug resolvedBug = new Bug("fixed bug", "description", BugType.BUG, contextHolder.getCurrentTrader());

        openedBug = bugService.save(openedBug);
        resolvedBug = bugService.save(resolvedBug);

        Page<Bug> activeBugs = bugService.findActiveBugs(new PageRequest(0, 20));
        Page<Bug> resolvedBugs = bugService.findResolvedBugs(new PageRequest(0, 20));

        Assert.assertTrue(activeBugs.getTotalElements() > activeBugsCount);
        Assert.assertFalse(resolvedBugs.getTotalElements() > resolvedBugsCount);
        Assert.assertEquals(activeBugsCount + 2, activeBugs.getNumberOfElements());


        // Resolve bug
        boolean resolveResult = bugService.resolve(resolvedBug.getId(), BugStatus.FIXED);

        activeBugs = bugService.findActiveBugs(new PageRequest(0, 20));
        resolvedBugs = bugService.findResolvedBugs(new PageRequest(0, 20));

        Assert.assertTrue(resolveResult);
        Assert.assertTrue(activeBugs.hasContent());
        Assert.assertTrue(resolvedBugs.hasContent());
        Assert.assertEquals(activeBugsCount + 1, activeBugs.getNumberOfElements());
        Assert.assertEquals(resolvedBugsCount + 1, resolvedBugs.getNumberOfElements());

        final Bug finalOpenedBug = openedBug;
        final Bug finalResolvedBug = resolvedBug;
        Assert.assertTrue(activeBugs.getContent().stream().anyMatch(b -> b.getId().equals(finalOpenedBug.getId())));
        Assert.assertTrue(resolvedBugs.getContent().stream().anyMatch(b -> b.getId().equals(finalResolvedBug.getId())));
    }


    @Test
    @WithMockCustomUser
    public void testUpdateStatus() throws PlatformException {
        Bug bug = new Bug("bug 1", "description", BugType.BUG, contextHolder.getCurrentTrader());
        bug = bugService.save(bug);

        bug = bugService.findById(bug.getId());
        Assert.assertNotNull(bug);
        Assert.assertEquals(BugStatus.OPENED, bug.getStatus());

        bug = bugService.updateStatus(bug.getId(), BugStatus.CANNOT_REPRODUCE);
        Assert.assertNotNull(bug);

        bug = bugService.findById(bug.getId());
        Assert.assertNotNull(bug);
        Assert.assertEquals(BugStatus.CANNOT_REPRODUCE, bug.getStatus());
    }


    @Test
    @WithMockCustomUser
    public void testAddComment() {
        Bug bug = new Bug("bug with comments", "description", BugType.BUG, contextHolder.getCurrentTrader());
        bug = bugService.save(bug);

        int zeroBugCommentsCount = bugService.getBugCommentsCount(bug.getId());
        Assert.assertEquals(0, zeroBugCommentsCount);

        BugComment firstComment = bugService.addComment(bug.getId(), "first comment");
        BugComment secondComment = bugService.addComment(bug.getId(), "second comment");

        Assert.assertNotNull(firstComment);
        Assert.assertNotNull(secondComment);

        int bugCommentsCount = bugService.getBugCommentsCount(bug.getId());
        Assert.assertEquals(2, bugCommentsCount);

        BugComment firstCommentById = bugService.findCommentById(firstComment.getId());
        Assert.assertNotNull(firstCommentById);
        Assert.assertEquals(firstComment.getId(), firstCommentById.getId());

        Page<BugComment> comments = bugService.findBugComments(bug.getId(), new PageRequest(0, 20));
        Assert.assertEquals(2, comments.getTotalElements());
        Assert.assertEquals(firstComment.getId(), comments.getContent().get(0).getId());
        Assert.assertEquals(secondComment.getId(), comments.getContent().get(1).getId());
    }


    @Test
    @WithMockCustomUser
    public void testUpdateComment() throws PlatformException {
        String commentText = "first comment";
        String newCommentText = "new_comment_text";
        Bug bug = new Bug("bug with comments", "description", BugType.BUG, contextHolder.getCurrentTrader());
        bug = bugService.save(bug);

        BugComment bugComment = bugService.addComment(bug.getId(), commentText);

        BugComment oldComment = bugService.findCommentById(bugComment.getId());
        Assert.assertEquals(commentText, oldComment.getComment());

        bugService.updateComment(bugComment.getId(), newCommentText);
        BugComment newComment = bugService.findCommentById(bugComment.getId());
        Assert.assertEquals(newCommentText, newComment.getComment());
    }


    @Test
    @WithMockCustomUser
    public void testRemoveComment() throws PlatformException {
        Bug bug = new Bug("bug with comments", "description", BugType.BUG, contextHolder.getCurrentTrader());
        bug = bugService.save(bug);

        BugComment bugComment = bugService.addComment(bug.getId(), "comment to remove");

        BugComment foundComment = bugService.findCommentById(bugComment.getId());
        Assert.assertNotNull(foundComment);

        bugService.removeComment(foundComment.getId());
        foundComment = bugService.findCommentById(bugComment.getId());
        Assert.assertNull(foundComment);
    }


    @Test
    @WithMockCustomUser
    public void testVote() throws PlatformException {
        Bug bug = new Bug("feature for vote", "description", BugType.NEW_FEATURE, contextHolder.getCurrentTrader());
        bug = bugService.save(bug);

        int zeroBugVotesCount = bug.getVoters().size();
        Assert.assertEquals(0, zeroBugVotesCount);

        boolean voteResult = bugService.vote(bug.getId());
        Assert.assertTrue(voteResult);

        bug = bugService.findById(bug.getId());
        int bugVotesCount = bug.getVoters().size();
        Assert.assertEquals(1, bugVotesCount);
    }
}
