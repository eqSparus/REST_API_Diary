package ru.diary.services;

import ru.diary.models.UserAuth;

public interface UserService {

    String loginUser(UserAuth user);

    boolean registrationUser(UserAuth user);

}
