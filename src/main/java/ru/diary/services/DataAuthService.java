package ru.diary.services;

import ru.diary.models.form.UserAuth;

public interface DataAuthService {

    void resetPassword(UserAuth user);

}
