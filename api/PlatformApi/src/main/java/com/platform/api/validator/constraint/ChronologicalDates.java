package com.platform.api.validator.constraint;


@Documented
@Constraint(validatedBy = ChronologicalDateConstraintValidator.class)
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChronologicalDates {
    String message() default "{ChronologicalDates}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
