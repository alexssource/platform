package com.platform.data.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/27/16.
 */
public class CurrencyFactory {
    private String code;


    private CurrencyFactory() {
    }


    public Currency getCurrency() {
        return Currency.getInstance(code);
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }
}
