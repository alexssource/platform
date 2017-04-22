package com.platform.data.domain;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/18/17.
 */
@Entity
@Table(name = "BUG")
@SequenceGenerator(name = "SQ_BUG_GENERATOR", sequenceName = "SQ_BUG", allocationSize = 1)
public class Bug {

    @Id
    @GeneratedValue(generator = "SQ_BUG_GENERATOR")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private BugStatus status = BugStatus.OPENED;

    @Column(name = "BUG_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private BugType type;

    @Column(name = "CREATED_DATE", nullable = false)
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Column(name = "RESOLVED_DATE")
    private ZonedDateTime resolvedDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private Trader trader;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "BUG_VOTE",
            joinColumns = @JoinColumn(name = "BUG_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private List<Trader> voters = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "BUG_ID")
    private List<BugComment> comments = new ArrayList<>();


    public Bug() {
    }


    public Bug(String name, String description, BugType type, Trader trader)
    {
        this.name = name;
        this.description = description;
        this.type = type;
        this.trader = trader;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public BugStatus getStatus() {
        return status;
    }


    public void setStatus(BugStatus status) {
        this.status = status;
    }


    public BugType getType() {
        return type;
    }


    public void setType(BugType type) {
        this.type = type;
    }


    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }


    public ZonedDateTime getResolvedDate() {
        return resolvedDate;
    }


    public void setResolvedDate(ZonedDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }


    public Trader getTrader() {
        return trader;
    }


    public void setTrader(Trader trader) {
        this.trader = trader;
    }


    public List<Trader> getVoters() {
        return voters;
    }


    public void setVoters(List<Trader> voters) {
        this.voters = voters;
    }


    public List<BugComment> getComments() {
        return comments;
    }


    public void setComments(List<BugComment> comments) {
        this.comments = comments;
    }


    public void addComment(BugComment bugComment)
    {
        comments.add(bugComment);
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
