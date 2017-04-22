package com.platform.library.dto.exception;


/**
 * Created by alex on 2/18/17.
 */
public class PlatformFieldValidationException extends Exception {
    private List<PlatformFieldErrorDto> errors;


    public PlatformFieldValidationException(List<PlatformFieldErrorDto> errors) {
        this.errors = errors;
    }


    public List<PlatformFieldErrorDto> getErrors() {
        return errors;
    }


    public void setErrors(List<PlatformFieldErrorDto> errors) {
        this.errors = errors;
    }
}
