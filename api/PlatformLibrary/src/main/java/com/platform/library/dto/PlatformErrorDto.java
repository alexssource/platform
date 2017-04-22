package com.platform.library.dto;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 28.12.16.
 */
public class PlatformErrorDto {
    private String code;

    private String message;


    public PlatformErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
