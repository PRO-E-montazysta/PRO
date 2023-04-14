package com.emontazysta.validation;

import com.emontazysta.configuration.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlPhoneNumberValidator implements ConstraintValidator<PlPhone, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumber.matches(Constants.PL_PHONE_NUMBER_REGEX);
    }
}
