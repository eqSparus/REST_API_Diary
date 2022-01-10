package ru.diary.repositories;

import ru.diary.models.User;

public interface IPersonalDataRepository {

    void updateName(User user);

    void updatePassword(User user);

}
