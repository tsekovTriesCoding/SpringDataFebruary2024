package org.example.springdataautomappingobjectsexercise.constants;

public enum Exceptions {
    ;
    public static final String INVALID_EMAIL_MESSAGE = "Incorrect email.";
    public static final String INVALID_PASSWORD = "Password length must be at least 6 symbols " +
            "and must contain at least 1 uppercase, 1 lowercase letter and 1 digit";
    public static final String INCORRECT_USERNAME_PASSWORD_MESSAGE = "Incorrect username / password";
    public static final String PASSWORDS_DO_NOT_MATCH = "Passwords don't match";
    public static final String NO_USER_LOGGED_IN = "Cannot log out. No user was logged in.";


    public static final String INVALID_GAME_TITLE = "Title has to begin with an uppercase letter and " +
            "must have length between 3 and 100 symbols";
    public static final String INVALID_GAME_PRICE = "Price has to be a positive number";
    public static final String INVALID_GAME_SIZE = "Size has to be a positive number";
    public static final String INVALID_TRAILER_ID_LENGTH = "ID should be exactly 11 characters long";
    public static final String INVALID_THUMBNAIL_URL = "Thumbnail url should start with http:// ot https://";
    public static final String INVALID_DESCRIPTION = "Description must be at least 20 symbols";
    public static final String USER_DOES_NOT_HAVE_ADMIN_RIGHTS = "User is not an administrator";
    public static final String GAME_DOES_NOT_EXIST = "Game does not exist";


}
