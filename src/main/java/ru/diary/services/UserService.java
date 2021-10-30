package ru.diary.services;

import ru.diary.models.form.UserAuth;

import java.util.Optional;

public interface UserService {

    Optional<String> loginUser(UserAuth user);

    boolean registrationUser(UserAuth user);

}
