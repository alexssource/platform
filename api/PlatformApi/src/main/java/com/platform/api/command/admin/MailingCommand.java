package com.platform.api.command.admin;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/29/17.
 */
public class MailingCommand {

    @NotNull
    @Size(min = 10)
    private String mailingTemplate;


    public MailingCommand() {
    }


    public MailingCommand(String mailingTemplate) {
        this.mailingTemplate = mailingTemplate;
    }


    public String getMailingTemplate() {
        return mailingTemplate;
    }


    public void setMailingTemplate(String mailingTemplate) {
        this.mailingTemplate = mailingTemplate;
    }
}
