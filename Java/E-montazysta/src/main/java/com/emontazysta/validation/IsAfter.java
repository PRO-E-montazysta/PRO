package com.emontazysta.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = IsAfterDateValidator.class)
@Documented
public @interface IsAfter{
    String message() default "{startDateFieldName} must be after {endDateFieldName}";
    String startDateFieldName();
    String endDateFieldName();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
