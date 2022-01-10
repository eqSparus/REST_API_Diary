package ru.diary.services;

public interface IEmailService {

    boolean sendingEmail(String email);
    void updateData(String email);
}
