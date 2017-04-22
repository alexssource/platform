package com.platform.api.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/16/17.
 */
public class EntityDtoMapper {

    public static TraderDto mapTrader(Trader trader) {
        return new TraderDto(trader.getId(), trader.getEmail(), trader.isEnabled(), trader.getCreatedAt(),
                trader.isLocked());
    }


    public static BugDto mapBug(Bug bug, int votesCount, int commentsCount, Trader currentUser)
    {
        TraderDto traderDto = mapTrader(bug.getTrader());
        boolean hasVoted = bug.getVoters().contains(currentUser);
        return new BugDto(bug.getId(), bug.getName(), bug.getDescription(), bug.getStatus().name(),
                bug.getType().name(), bug.getCreatedDate(),
                bug.getResolvedDate(), traderDto, votesCount, commentsCount, hasVoted);
    }


    public static BugCommentDto mapBugComment(BugComment bugComment) {
        return new BugCommentDto(bugComment.getId(), bugComment.getBugId(), bugComment.getComment(),
                bugComment.getTrader().getEmail(), bugComment.getCreatedDate());
    }


.......
}
