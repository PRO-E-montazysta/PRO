package com.emontazysta.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.PropertyUtils;

import java.time.LocalDateTime;

public class IsAfterDateValidator implements ConstraintValidator<IsAfter, Object> {

    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(IsAfter annotation) {
        startDateFieldName = annotation.startDateFieldName();
        endDateFieldName = annotation.endDateFieldName();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            LocalDateTime startDate = (LocalDateTime) PropertyUtils.getProperty(object, startDateFieldName);
            LocalDateTime endDate = (LocalDateTime) PropertyUtils.getProperty(object, endDateFieldName);
            return startDate == null || endDate == null || endDate.isAfter(startDate);
        } catch (Exception e) {
            return false;
        }
    }
}
