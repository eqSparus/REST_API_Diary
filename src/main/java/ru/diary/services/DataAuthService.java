package ru.diary.services;

import ru.diary.models.form.UserAuth;

public interface DataAuthService {

    void updatePassword(UserAuth user);

    void updateName(UserAuth user);

}
