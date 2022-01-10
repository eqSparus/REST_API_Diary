package ru.diary.services.auth;

public class RegistrationErrorLoginExists extends Exception{

    public RegistrationErrorLoginExists() {
    }

    public RegistrationErrorLoginExists(String message) {
        super(message);
    }
}
