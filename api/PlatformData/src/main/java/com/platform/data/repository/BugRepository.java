package com.platform.data.repository;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/18/17.
 */
public interface BugRepository extends PagingAndSortingRepository<Bug, Integer> {
    @Query("select b from Bug b left join b.voters voters where b.status in ('OPENED', 'IN_PROGRESS') group by b.id order by COUNT(voters) desc")
    Page<Bug> findActiveBugs(Pageable pageable);

    @Query("select b from Bug b where b.status in ('FIXED', 'WONT_FIX', 'CANNOT_REPRODUCE')")
    Page<Bug> findResolvedBugs(Pageable pageable);

    @Query("select b from Bug b where b.status in ('FIXED', 'WONT_FIX', 'CANNOT_REPRODUCE') and (b.resolvedDate between :startDate and :endDate) order by b.resolvedDate")
    List<Bug> getBugsFixedInPeriod(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    @Query("select b from Bug b where b.status in ('OPENED', 'IN_PROGRESS') and (b.createdDate between :startDate and :endDate) order by b.createdDate")
    List<Bug> getBugsCreatedInPeriod(@Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);
}
