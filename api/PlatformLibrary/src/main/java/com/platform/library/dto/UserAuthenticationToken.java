package com.platform.library.dto;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String token;


    public UserAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }


    public UserAuthenticationToken(String token, Object principal, String credentials,
            Collection<? extends GrantedAuthority> authorities)
    {
        super(principal, credentials, authorities);
        this.token = token;
    }


    public String getToken() {
        return token;
    }
}