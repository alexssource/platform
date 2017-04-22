package com.platform.library.dto;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 2/18/17.
 */
public class BugDto {
    private int id;

    private String name;

    private String description;

    private String status;

    private String type;

    private ZonedDateTime createdDate;

    private ZonedDateTime resolvedDate;

    private TraderDto user;

    private int votesCount;

    private int commentsCount;

    /**
     * Голосовал ли за этот баг текущий пользователь
     */
    private boolean hasVoted;


    public BugDto(int id, String name, String description, String status, String type, ZonedDateTime createdDate,
            ZonedDateTime resolvedDate,
            TraderDto user, int votesCount, int commentsCount, boolean hasVoted)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.createdDate = createdDate;
        this.resolvedDate = resolvedDate;
        this.user = user;
        this.votesCount = votesCount;
        this.commentsCount = commentsCount;
        this.hasVoted = hasVoted;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
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


    public TraderDto getUser() {
        return user;
    }


    public void setUser(TraderDto user) {
        this.user = user;
    }


    public int getVotesCount() {
        return votesCount;
    }


    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
    }


    public int getCommentsCount() {
        return commentsCount;
    }


    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }


    public boolean isHasVoted() {
        return hasVoted;
    }


    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}
