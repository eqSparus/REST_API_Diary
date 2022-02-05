package ru.diary.services.auth.exeption;

public class RegistrationErrorLoginExistsException extends Exception {

    public RegistrationErrorLoginExistsException() {
    }

    public RegistrationErrorLoginExistsException(String message) {
        super(message);
    }
}
