package org.example.usersystem.annotations.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.usersystem.constants.Constants;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default Constants.INVALID_PASSWORD_FORMAT;

    int minLength();

    int maxLength();

    boolean containsDigit() default false;

    boolean containsLowerCase() default false;

    boolean containsUpperCase() default false;

    boolean containsSpecialSymbols() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
