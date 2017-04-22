package com.platform.api.command;



/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class LoginCommand {

    @NotNull
    @Size(min = 4, max = 32)
    private String email;

    @NotNull
    @Size(min = 8, max = 32)
    private String password;


    public LoginCommand() {

    }


    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
