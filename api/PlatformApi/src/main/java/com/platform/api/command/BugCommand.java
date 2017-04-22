package com.platform.api.command;



/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/1/17.
 */
public class BugCommand {

    private Integer id;

    @NotNull
    @Size(min = 6, max = 128)
    private String name;

    @NotNull
    @Size(min = 6)
    private String description;

    @NotNull
    private BugType type;


    public BugCommand() {
    }


    public BugCommand(Integer id, String name, String description, BugType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
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


    public BugType getType() {
        return type;
    }


    public void setType(BugType type) {
        this.type = type;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
