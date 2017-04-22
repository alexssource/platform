package com.platform.api.command;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class RegistrationCommand {

    @NotNull
    @Size(min = 4, max = 32)
    private String email;


    public RegistrationCommand() {
    }


    public RegistrationCommand(String email) {
        this.email = email;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}
