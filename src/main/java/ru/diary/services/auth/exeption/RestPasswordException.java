package ru.diary.services.auth.exeption;

public class RestPasswordException extends Exception{

    public RestPasswordException() {
    }

    public RestPasswordException(String message) {
        super(message);
    }
}
