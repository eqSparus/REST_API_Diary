package ru.diary.services;

import ru.diary.models.dto.UserAuth;
import ru.diary.services.auth.exeption.AuthException;
import ru.diary.services.auth.exeption.RegistrationErrorLoginExistsException;

public interface IAuthenticationService {

    String loginUser(UserAuth user) throws AuthException;

    String registrationUser(UserAuth user) throws RegistrationErrorLoginExistsException;

}
