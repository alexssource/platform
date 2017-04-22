package com.platform.api.command;



/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/4/17.
 */
public class BugCommentCommand {

    private Integer id;

    @NotNull
    private Integer bugId;

    @NotNull
    @Size(min = 2, max = 256)
    private String comment;


    public BugCommentCommand() {
    }


    public BugCommentCommand(Integer bugId, String comment) {
        this.bugId = bugId;
        this.comment = comment;
    }


    public BugCommentCommand(Integer id, Integer bugId, String comment) {
        this.id = id;
        this.bugId = bugId;
        this.comment = comment;
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


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }
}
