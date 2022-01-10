package ru.diary.services;

import ru.diary.models.dto.UserAuth;

public interface IDataAuthService {

    void resetPassword(UserAuth user);

}
