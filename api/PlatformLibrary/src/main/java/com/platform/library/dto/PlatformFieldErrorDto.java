package com.platform.library.dto;


import java.util.Objects;


/**
 * Created by alex on 2/18/17.
 */
public class PlatformFieldErrorDto extends PlatformErrorDto {
    private String field;


    public PlatformFieldErrorDto(String field, String code, String message) {
        super(code, message);
        this.field = field;
    }


    public String getField() {
        return field;
    }


    public void setField(String field) {
        this.field = field;
    }


    @Override
    public int hashCode() {
        return Objects.hash(field);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlatformFieldErrorDto that = (PlatformFieldErrorDto) o;
        return Objects.equals(field, that.field);
    }
}
