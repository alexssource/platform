package com.platform.api.controller;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/1/17.
 */
@RestController
@RequestMapping("/api/bug")
public class BugController {
    private final static Logger logger = LoggerFactory.getLogger(BugController.class);

    @Autowired
    private BugService bugService;

    @Autowired
    private ContextHolder contextHolder;


    @Authorized
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public Page<BugDto> getActiveBugs(@PageableDefault(size = 15) Pageable pageable) {
        Page<Bug> activeBugs = bugService.findActiveBugs(pageable);
        List<BugDto> bugDtos = activeBugs.getContent().stream()
                .map(bug -> EntityDtoMapper.mapBug(bug, bug.getVoters().size(),
                        bugService.getBugCommentsCount(bug.getId()),
                        contextHolder.getCurrentTrader())
                )
                .collect(Collectors.toList());
        return new PageImpl<>(bugDtos, pageable, activeBugs.getTotalElements());
    }


    @Authorized
    @RequestMapping(value = "/resolved", method = RequestMethod.GET)
    public Page<BugDto> getResolvedBugs(
            @PageableDefault(size = 20, sort = "resolvedDate", direction = Sort.Direction.DESC) Pageable pageable)
    {
        Page<Bug> resolvedBugs = bugService.findResolvedBugs(pageable);
        List<BugDto> bugDtos = resolvedBugs.getContent().stream()
                .map(bug -> EntityDtoMapper.mapBug(bug, bug.getVoters().size(),
                        bugService.getBugCommentsCount(bug.getId()),
                        contextHolder.getCurrentTrader())
                )
                .collect(Collectors.toList());
        return new PageImpl<>(bugDtos, pageable, resolvedBugs.getTotalElements());
    }


    @Authorized
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public BugDto createBug(@RequestBody @Valid BugCommand bugCommand, BindingResult bindingResult)
            throws PlatformFieldValidationException
    {
        if (bindingResult.hasErrors()) {
            throw new PlatformFieldValidationException(ValidationErrorHelper.createPlatformFieldErrors(bindingResult));
        }

        Trader currentUser = contextHolder.getCurrentTrader();
        Bug bug = new Bug(bugCommand.getName(), bugCommand.getDescription(), bugCommand.getType(), currentUser);
        bug = bugService.save(bug);

        logger.info("User {} created a new bug with type {} and id {}", currentUser.getEmail(), bug.getType(),
                bug.getId());
        return EntityDtoMapper.mapBug(bug, 0, 0, currentUser);
    }


    @Authorized
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BugDto editBug(@RequestBody @Valid BugCommand bugCommand, BindingResult bindingResult)
            throws PlatformException, PlatformFieldValidationException
    {
        if (bugCommand.getId() == null) {
            throw new PlatformException("Incorrect values provided");
        }

        if (bindingResult.hasErrors()) {
            throw new PlatformFieldValidationException(ValidationErrorHelper.createPlatformFieldErrors(bindingResult));
        }

        Trader currentUser = contextHolder.getCurrentTrader();
        Bug bug = bugService.findById(bugCommand.getId());

        if (!currentUser.equals(bug.getTrader())) {
            throw new BadCredentialsException("You can't edit the task from another author");
        }

        bug.setName(bugCommand.getName());
        bug.setDescription(bugCommand.getDescription());
        bug.setType(bugCommand.getType());
        bug = bugService.save(bug);

        logger.info("User {} updated a bug with type {} and id {}", currentUser.getEmail(), bug.getType(), bug.getId());
        return EntityDtoMapper.mapBug(bug, 0, 0, currentUser);
    }


    @Authorized
    @RequestMapping(value = "/{bugId}/comments", method = RequestMethod.GET)
    public Page<BugCommentDto> getBugComments(
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(name = "bugId") Integer bugId)
    {
        Page<BugComment> bugComments = bugService.findBugComments(bugId, pageable);
        List<BugCommentDto> commentDtos = bugComments.getContent().stream()
                .map(comment -> EntityDtoMapper.mapBugComment(comment))
                .collect(Collectors.toList());
        return new PageImpl<BugCommentDto>(commentDtos, pageable, bugComments.getTotalElements());
    }


    @Authorized
    @RequestMapping(value = "/comments/create", method = RequestMethod.POST)
    public BugCommentDto createComment(@RequestBody @Valid BugCommentCommand commentCommand,
            BindingResult bindingResult) throws PlatformException
    {
        if (bindingResult.hasErrors()) {
            throw new PlatformException("Incorrect values provided");
        }

        BugComment comment = bugService.addComment(commentCommand.getBugId(), commentCommand.getComment());
        if (comment == null) {
            logger.warn("The comment has not been created. BugId: {}, comment: {}", commentCommand.getBugId(),
                    commentCommand.getComment());
            return null;
        }
        return EntityDtoMapper.mapBugComment(comment);
    }


    @Authorized
    @RequestMapping(value = "/comments/edit", method = RequestMethod.POST)
    public BugCommentDto editComment(@RequestBody @Valid BugCommentCommand commentCommand, BindingResult bindingResult)
            throws PlatformException, PlatformFieldValidationException
    {
        if (commentCommand.getId() == null) {
            throw new PlatformException("Incorrect values provided");
        }

        if (bindingResult.hasErrors()) {
            throw new PlatformFieldValidationException(ValidationErrorHelper.createPlatformFieldErrors(bindingResult));
        }

        Trader currentUser = contextHolder.getCurrentTrader();
        BugComment comment = bugService.findCommentById(commentCommand.getId());

        if (!currentUser.equals(comment.getTrader())) {
            throw new BadCredentialsException("You can't edit the comment from another author");
        }

        comment = bugService.updateComment(commentCommand.getId(), commentCommand.getComment());

        if (comment == null) {
            logger.warn("The comment has not been updated. BugId: {}, comment: {}", commentCommand.getBugId(),
                    commentCommand.getComment());
            return null;
        }

        return EntityDtoMapper.mapBugComment(comment);
    }


    @Authorized
    @RequestMapping(value = "/comments/{commentId}/delete", method = RequestMethod.GET)
    public void delete(@PathVariable(name = "commentId") Integer commentId) throws PlatformException {
        bugService.removeComment(commentId);
    }


    @Authorized
    @RequestMapping(value = "/{bugId}/vote", method = RequestMethod.GET)
    public boolean vote(@PathVariable(name = "bugId") Integer bugId) throws PlatformException {
        return bugService.vote(bugId);
    }


    @Secured(Role.Get.ROLE_ADMIN)
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public BugDto editBugStatus(@RequestBody @Valid BugStatusCommand command, BindingResult bindingResult)
            throws PlatformException, PlatformFieldValidationException
    {
        if (bindingResult.hasErrors()) {
            throw new PlatformFieldValidationException(ValidationErrorHelper.createPlatformFieldErrors(bindingResult));
        }

        Bug bug = bugService.updateStatus(command.getBugId(), command.getBugStatus());
        return EntityDtoMapper.mapBug(bug, 0, 0, null);
    }


    @Secured(Role.Get.ROLE_ADMIN)
    @RequestMapping(value = "/fixed/last/{days}/days")
    public List<BugDto> getRecentlyFixedBugs(@PathVariable("days") Integer days) {
        return bugService.findRecentlyFixedBugs(days).stream()
                .map(bug -> EntityDtoMapper.mapBug(bug, 0, 0, null))
                .collect(Collectors.toList());
    }


    @Secured(Role.Get.ROLE_ADMIN)
    @RequestMapping(value = "/created/last/{days}/days")
    public List<BugDto> getRecentlyCreatedBugs(@PathVariable("days") Integer days) {
        return bugService.findRecentlyCreatedBugs(days).stream()
                .map(bug -> EntityDtoMapper.mapBug(bug, 0, 0, null))
                .collect(Collectors.toList());
    }
}
