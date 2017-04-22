package com.platform.api.exception;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class JwtTokenMissingException extends AuthenticationException {

    public JwtTokenMissingException(String msg) {
        super(msg);
    }
}