package ru.diary.services;

import ru.diary.models.UserAuth;

public interface DataService {

    void updatePassword(UserAuth user);

    void updateName(UserAuth user);

}
