package com.platform.data.test.inregration.repository;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/18/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "development")
@ContextConfiguration(locations = {"classpath:beans.xml", "classpath:persistence.xml"})
public class BugRepositoryTestIntegration {

    @Autowired
    private BugRepository repository;

    @Autowired
    private ContextHolder contextHolder;


    @Test
    @WithMockCustomUser
    public void testFindAll()
    {
        long initialSize = repository.count();

        Bug bug = new Bug();
        bug.setName("Test bug 1");
        bug.setDescription("description");
        bug.setStatus(BugStatus.OPENED);
        bug.setType(BugType.BUG);
        bug.setTrader(contextHolder.getCurrentTrader());
        bug = repository.save(bug);

        Iterable<Bug> bugs = repository.findAll();
        Assert.assertNotNull(bugs);
        Assert.assertEquals(1 + initialSize, repository.count());
    }


    @Test
    @WithMockCustomUser
    public void testGetBugsFixedInPeriod() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.systemDefault());
        Trader trader = contextHolder.getCurrentTrader();

        Bug bug1 = BugFactory.createBug("unresolved bug", BugType.BUG, trader);
        Bug bug2 = BugFactory.createResolvedBug("earlier resolved bug", ZonedDateTime.parse("01-01-2009 00:00", formatter), trader);
        Bug bug3 = BugFactory.createResolvedBug("first range resolved bug", BugStatus.CANNOT_REPRODUCE, ZonedDateTime.parse("07-06-2015 04:03", formatter), trader);
        Bug bug4 = BugFactory.createResolvedBug("second range resolved bug", BugStatus.WONT_FIX, ZonedDateTime.parse("14-06-2015 01:07", formatter), trader);
        Bug bug5 = BugFactory.createResolvedBug("third range resolved bug", BugStatus.FIXED, ZonedDateTime.parse("16-06-2015 01:12", formatter), trader);
        Bug bug6 = BugFactory.createResolvedBug("later resolved bug", BugStatus.FIXED, ZonedDateTime.parse("16-06-2015 01:13", formatter), trader);

        bug1 = repository.save(bug1);
        bug2 = repository.save(bug2);
        bug3 = repository.save(bug3);
        bug4 = repository.save(bug4);
        bug5 = repository.save(bug5);
        bug6 = repository.save(bug6);

        List<Bug> bugsFixedInPeriod = repository
                .getBugsFixedInPeriod(ZonedDateTime.parse("01-01-2009 00:01", formatter),
                        ZonedDateTime.parse("16-06-2015 01:12", formatter));

        Assert.assertEquals(3, bugsFixedInPeriod.size());
        Assert.assertEquals(bug3.getId(), bugsFixedInPeriod.get(0).getId());
        Assert.assertEquals(bug4.getId(), bugsFixedInPeriod.get(1).getId());
        Assert.assertEquals(bug5.getId(), bugsFixedInPeriod.get(2).getId());
    }


    @Test
    @WithMockCustomUser
    public void testGetBugsCreatedInPeriod() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").withZone(ZoneId.systemDefault());
        Trader trader = contextHolder.getCurrentTrader();

        Bug bug1 = BugFactory.createBug("resolved bug", BugType.BUG, BugStatus.WONT_FIX, trader);
        Bug bug2 = BugFactory.createBug(" earlier created bug", BugType.BUG, BugStatus.IN_PROGRESS, trader);
        Bug bug3 = BugFactory.createBug(" first range created bug", BugType.BUG, BugStatus.IN_PROGRESS, trader);
        Bug bug4 = BugFactory.createBug(" second created bug", BugType.BUG, BugStatus.OPENED, trader);
        Bug bug5 = BugFactory.createBug(" third created bug", BugType.BUG, BugStatus.IN_PROGRESS, trader);
        Bug bug6 = BugFactory.createBug(" later created bug", BugType.BUG, BugStatus.IN_PROGRESS, trader);

        bug1.setCreatedDate(ZonedDateTime.parse("04-04-2007 00:00", formatter));
        bug2.setCreatedDate(ZonedDateTime.parse("07-03-2007 01:01", formatter));
        bug3.setCreatedDate(ZonedDateTime.parse("07-03-2007 01:02", formatter));
        bug4.setCreatedDate(ZonedDateTime.parse("08-04-2007 00:00", formatter));
        bug5.setCreatedDate(ZonedDateTime.parse("09-06-2007 06:02", formatter));
        bug6.setCreatedDate(ZonedDateTime.parse("09-06-2007 06:03", formatter));

        bug1 = repository.save(bug1);
        bug2 = repository.save(bug2);
        bug3 = repository.save(bug3);
        bug4 = repository.save(bug4);
        bug5 = repository.save(bug5);
        bug6 = repository.save(bug6);

        List<Bug> bugsFixedInPeriod = repository
                .getBugsCreatedInPeriod(ZonedDateTime.parse("07-03-2007 01:02", formatter),
                        ZonedDateTime.parse("09-06-2007 06:02", formatter));

        Assert.assertEquals(3, bugsFixedInPeriod.size());
        Assert.assertEquals(bug3.getId(), bugsFixedInPeriod.get(0).getId());
        Assert.assertEquals(bug4.getId(), bugsFixedInPeriod.get(1).getId());
        Assert.assertEquals(bug5.getId(), bugsFixedInPeriod.get(2).getId());
    }
}
