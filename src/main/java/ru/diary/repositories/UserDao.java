package ru.diary.repositories;

import ru.diary.models.User;

import java.util.Optional;

public interface UserDao {

    void create(User user);

    Optional<User> findUserByEmail(String email);

    void updateStatusActive(String email);

}
