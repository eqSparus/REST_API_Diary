package ru.diary.services;

import ru.diary.models.User;
import ru.diary.models.UserAuth;

import java.util.Optional;

public interface DataService {

    void updatePassword(UserAuth user);

    void updateName(UserAuth user);

    Optional<User> getUserByEmail(String email);

}
