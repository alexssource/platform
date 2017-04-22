package com.platform.data.test.inregration.repository;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "development")
@ContextConfiguration(locations = {"classpath:beans.xml", "classpath:persistence.xml"})
public class BugCommentRepositoryTestIntegration {

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private BugCommentRepository bugCommentRepository;

    @Autowired
    private ContextHolder contextHolder;


    @Test
    @WithMockCustomUser
    public void testComments() {
        int initialSize = bugCommentRepository.findAll().size();
        Bug bug = new Bug();
        bug.setName("Test bug 1");
        bug.setDescription("description");
        bug.setStatus(BugStatus.OPENED);
        bug.setType(BugType.BUG);
        bug.setTrader(contextHolder.getCurrentTrader());
        bug = bugRepository.save(bug);

        BugComment comment = new BugComment();
        comment.setTrader(contextHolder.getCurrentTrader()); // сам себя комментит
        comment.setComment("Test comment from owner");
        comment.setBugId(bug.getId());
        bugCommentRepository.save(comment);

        List<BugComment> all = bugCommentRepository.findAll();

        Assert.assertNotNull(all);
        Assert.assertEquals(1 + initialSize, all.size());
    }

}
