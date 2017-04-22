

/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/18/17.
 */
@Entity
@Table(name = "BUG_COMMENT")
@NamedQueries({
        @NamedQuery(name = BugComment.QUERY_FIND_VOTES_COUNT_BY_BUG_ID,
                query = "select count(b.voters) from Bug b where b.id = :bugId")
})
@SequenceGenerator(name = "SQ_BUG_COMMENT_GENERATOR", sequenceName = "SQ_BUG_COMMENT", allocationSize = 1)
public class BugComment {
    public static final String QUERY_FIND_VOTES_COUNT_BY_BUG_ID = "Bug.findVotesCountByBugId";

    @Id
    @GeneratedValue(generator = "SQ_BUG_COMMENT_GENERATOR")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "BUG_ID", nullable = false)
    private Integer bugId;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Column(name = "CREATED_DATE", nullable = false)
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private Trader trader;


    public BugComment() {
    }


    public BugComment(Integer bugId, String comment, Trader trader) {
        this.bugId = bugId;
        this.comment = comment;
        this.trader = trader;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }


    public Trader getTrader() {
        return trader;
    }


    public void setTrader(Trader trader) {
        this.trader = trader;
    }


    public Integer getBugId() {
        return bugId;
    }


    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }
}
