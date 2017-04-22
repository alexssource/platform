package com.platform.library.dto;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/4/17.
 */
public class BugCommentDto {
    private Integer id;

    private Integer bugId;

    private String comment;

    private String owner;

    private ZonedDateTime createdDate;


    public BugCommentDto(Integer id, Integer bugId, String comment, String owner, ZonedDateTime createdDate) {
        this.id = id;
        this.bugId = bugId;
        this.comment = comment;
        this.owner = owner;
        this.createdDate = createdDate;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getBugId() {
        return bugId;
    }


    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getOwner() {
        return owner;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }


    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
