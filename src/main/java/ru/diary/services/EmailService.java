package ru.diary.services;

public interface EmailService {

    boolean sendingEmail(String email);
    void updateData(String email);
}
