package ru.diary.services.auth;

public class RestPasswordException extends Exception{

    public RestPasswordException() {
    }

    public RestPasswordException(String message) {
        super(message);
    }
}
