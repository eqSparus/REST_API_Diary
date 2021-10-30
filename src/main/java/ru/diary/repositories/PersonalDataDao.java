package ru.diary.repositories;

import ru.diary.models.User;

public interface PersonalDataDao {

    void updateName(User user);

    void updatePassword(User user);

}
