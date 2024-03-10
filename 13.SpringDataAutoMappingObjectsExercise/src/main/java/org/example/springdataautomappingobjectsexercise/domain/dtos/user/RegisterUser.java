package org.example.springdataautomappingobjectsexercise.domain.dtos.user;

import java.util.regex.Pattern;

import static org.example.springdataautomappingobjectsexercise.constants.Exceptions.*;
import static org.example.springdataautomappingobjectsexercise.constants.Validations.EMAIL_PATTERN;
import static org.example.springdataautomappingobjectsexercise.constants.Validations.PASSWORD_PATTERN;

public class RegisterUser {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public RegisterUser(String email, String password, String confirmPassword, String fullName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setConfirmPassword(confirmPassword);
        this.setFullName(fullName);
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        boolean isEmailValid = Pattern.matches(EMAIL_PATTERN, email);

        if (!isEmailValid) {
            throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
        }

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        boolean isPasswordValid = Pattern.matches(PASSWORD_PATTERN, password);

        if (!isPasswordValid) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }

        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (!this.password.equals(confirmPassword)) {
            throw new IllegalArgumentException(PASSWORDS_DO_NOT_MATCH);
        }

        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String successfulRegistration() {
        return this.fullName + " was registered";
    }
}
