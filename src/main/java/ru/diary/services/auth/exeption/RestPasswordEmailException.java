package ru.diary.services.auth.exeption;

public class RestPasswordEmailException extends Exception{

    public RestPasswordEmailException() {
    }

    public RestPasswordEmailException(String message) {
        super(message);
    }
}
