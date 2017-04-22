package com.platform.api.helper;


/**
 * Создает список ошибок для полей команды для создания PlatformFieldValidationException
 * <p>
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/8/17.
 */
public class ValidationErrorHelper {
    public static List<PlatformFieldErrorDto> createPlatformFieldErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(objectError -> (FieldError) objectError)
                .map(fieldError -> new PlatformFieldErrorDto(fieldError.getField(), fieldError.getCode(),
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
