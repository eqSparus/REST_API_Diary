package ru.diary.services;

public interface EmailService {

    void emailActiveMail(String email);
    void updateUserActive(String email);

}
