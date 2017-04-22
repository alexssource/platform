package com.platform.data.service.impl;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/18/17.
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BugServiceImpl implements BugService {
    private final static Logger logger = LoggerFactory.getLogger(BugServiceImpl.class);

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private BugCommentRepository bugCommentRepository;

    @Autowired
    private ContextHolder contextHolder;


    @Override
    public Page<Bug> findActiveBugs(Pageable pageable) {
        return bugRepository.findActiveBugs(pageable);
    }


    @Override
    public Page<Bug> findResolvedBugs(Pageable pageable) {
        return bugRepository.findResolvedBugs(pageable);
    }


    @Override
    public Page<BugComment> findBugComments(Integer bugId, Pageable pageable) {
        return bugCommentRepository.findByBugId(bugId, pageable);
    }


    @Override
    public Bug findById(Integer bugId) {
        return bugRepository.findOne(bugId);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Bug save(Bug bug) {
        return bugRepository.save(bug);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Bug updateStatus(Integer bugId, BugStatus status) throws PlatformException {
        Bug bug = findById(bugId);

        if (bug == null) {
            throw new PlatformException("Could not find the bug");
        }

        bug.setStatus(status);

        switch (status) {
            case FIXED:
            case WONT_FIX:
            case CANNOT_REPRODUCE:
                bug.setResolvedDate(ZonedDateTime.now());
                break;
        }

        return bug;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BugComment addComment(Integer bugId, String comment) {
        BugComment bugComment = new BugComment(bugId, comment, contextHolder.getCurrentTrader());
        return bugCommentRepository.save(bugComment);
    }


    @Override
    public BugComment findCommentById(Integer commentId) {
        return bugCommentRepository.findOne(commentId);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BugComment updateComment(Integer commentId, String comment) throws PlatformException {
        BugComment bugComment = findCommentById(commentId);

        if (bugComment == null) {
            logger.warn("Could not find comment with id: {}", commentId);
            throw new PlatformException("Could not find comment");
        }

        Trader currentUser = contextHolder.getCurrentTrader();

        if (!currentUser.equals(bugComment.getTrader())) {
            logger.warn("Attempt to edit comment from another author. Editor: {}", currentUser.getEmail());
            throw new BadCredentialsException("You can't edit the comment from another author");
        }

        bugComment.setComment(comment);
        return bugComment;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeComment(Integer commentId) throws PlatformException {
        BugComment comment = bugCommentRepository.findOne(commentId);

        if (comment == null) {
            logger.warn("Could not find comment with id {} to remove", commentId);
            throw new PlatformException("Comment not found");
        }

        Trader currentUser = contextHolder.getCurrentTrader();

        if (!currentUser.equals(comment.getTrader())) {
            logger.warn("Attempt to delete comment from another author. Editor: {}", currentUser.getEmail());
            throw new BadCredentialsException("You can't remove the comment from another author");
        }

        bugCommentRepository.delete(comment);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean resolve(Integer bugId, BugStatus toStatus) {
        Bug bug = bugRepository.findOne(bugId);

        if (bug == null) {
            logger.warn("Could not find bug {} to resolve", bugId);
            return false;
        }

        if (toStatus.equals(bug.getStatus())) {
            logger.warn("Bug {} is already in status {}", bugId, bug.getStatus());
            return false;
        }

        bug.setStatus(toStatus);
        return true;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean vote(Integer bugId) throws PlatformException {
        Bug bug = bugRepository.findOne(bugId);
        Trader votingTrader = contextHolder.getCurrentTrader();

        if (bug == null) {
            logger.warn("Could not find bug {} to resolve", bugId);
            return false;
        }

        if (bug.getVoters().contains(votingTrader)) {
            logger.warn("User {} has already voted for bugId {}", votingTrader.getEmail(), bugId);
            throw new PlatformException("You are already voted for this bug");
        }

        logger.info("User {} is voted for bug {}", votingTrader.getEmail(), bug.getId());
        return bug.getVoters().add(votingTrader);
    }


    @Override
    public int getBugCommentsCount(Integer bugId) {
        return bugCommentRepository.getCommentsCount(bugId);
    }


    @Override
    public List<Bug> findRecentlyFixedBugs(int daysCount) {
        ZonedDateTime today = ZonedDateTime.now();
        ZonedDateTime startDay = today.minusDays(daysCount);
        return bugRepository.getBugsFixedInPeriod(startDay, today);
    }


    @Override
    public List<Bug> findRecentlyCreatedBugs(int daysCount) {
        ZonedDateTime today = ZonedDateTime.now();
        ZonedDateTime startDay = today.minusDays(daysCount);
        return bugRepository.getBugsCreatedInPeriod(startDay, today);
    }
}
