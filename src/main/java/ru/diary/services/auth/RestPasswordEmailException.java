package ru.diary.services.auth;

public class RestPasswordEmailException extends Exception{

    public RestPasswordEmailException() {
    }

    public RestPasswordEmailException(String message) {
        super(message);
    }
}
