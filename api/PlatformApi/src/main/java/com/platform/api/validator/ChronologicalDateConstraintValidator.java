package com.platform.api.validator;


public class ChronologicalDateConstraintValidator
        implements ConstraintValidator<ChronologicalDates, RemovedItemCommand>
{

    @Override
    public void initialize(ChronologicalDates constraintAnnotation) {

    }


    @Override
    public boolean isValid(RemovedItemCommand item, ConstraintValidatorContext context) {
        if (item == null) {
            return false;
        }
        Instant startDate = item.getStartDateTime();
        Instant endDate = item.getEndDateTime();

        return startDate.isBefore(endDate);
    }
}
