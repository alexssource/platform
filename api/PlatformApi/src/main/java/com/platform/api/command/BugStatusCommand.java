package com.platform.api.command;



/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/7/17.
 */
public class BugStatusCommand {

    @NotNull
    private Integer bugId;

    @NotNull
    private BugStatus bugStatus;


    public BugStatusCommand() {
    }


    public BugStatusCommand(Integer bugId, BugStatus bugStatus) {
        this.bugId = bugId;
        this.bugStatus = bugStatus;
    }


    public Integer getBugId() {
        return bugId;
    }


    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }


    public BugStatus getBugStatus() {
        return bugStatus;
    }


    public void setBugStatus(BugStatus bugStatus) {
        this.bugStatus = bugStatus;
    }
}
