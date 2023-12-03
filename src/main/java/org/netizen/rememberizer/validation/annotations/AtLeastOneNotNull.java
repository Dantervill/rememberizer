package org.netizen.rememberizer.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.netizen.rememberizer.validation.validators.AtLeastOneNotNullValidator;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AtLeastOneNotNullValidator.class)
public @interface AtLeastOneNotNull {

    String message() default "At least one field must not be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
