package com.platform.data.repository;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/18/17.
 */
public interface BugCommentRepository extends JpaRepository<BugComment, Integer> {

    @Query("select count(c.bugId) from BugComment c where c.bugId = :bugId")
    Integer getCommentsCount(@Param("bugId") Integer bugId);

    Page<BugComment> findByBugId(Integer bugId, Pageable pageable);
}
