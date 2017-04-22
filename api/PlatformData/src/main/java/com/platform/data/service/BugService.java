package com.platform.data.service;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/18/17.
 */
public interface BugService {
    Page<Bug> findActiveBugs(Pageable pageable);

    Page<Bug> findResolvedBugs(Pageable pageable);

    Page<BugComment> findBugComments(Integer bugId, Pageable pageable);

    Bug findById(Integer bugId);

    Bug save(Bug bug);

    Bug updateStatus(Integer bugId, BugStatus status) throws PlatformException;

    BugComment addComment(Integer bugId, String comment);

    BugComment findCommentById(Integer commentId);

    BugComment updateComment(Integer commentId, String comment) throws PlatformException;

    void removeComment(Integer commentId) throws PlatformException;

    boolean resolve(Integer bugId, BugStatus toStatus);

    boolean vote(Integer bugId) throws PlatformException;

    int getBugCommentsCount(Integer bugId);

    /**
     * Получает список багов, которые были пофикшены за указанное количество дней назад
     *
     * @param daysCount количество дней назад, в течении которых были пофикшены искомые баги
     * @return список багов за указанный период
     */
    List<Bug> findRecentlyFixedBugs(int daysCount);

    /**
     * Получает список багов, которые были созданы за указанное количество дней назад
     *
     * @param daysCount количество дней назад, в течении которых были созданы искомые баги
     * @return список багов за указанный период
     */
    List<Bug> findRecentlyCreatedBugs(int daysCount);
}
