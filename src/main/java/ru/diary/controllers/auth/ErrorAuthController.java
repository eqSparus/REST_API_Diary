package ru.diary.controllers.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diary.services.auth.AuthException;
import ru.diary.services.auth.RegistrationErrorLoginExists;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ControllerAdvice(basePackageClasses = AuthenticationController.class)
public class ErrorAuthController {

    static String MESSAGE = "message";

    @ExceptionHandler(value = AuthException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ResponseBody
    public Map<String, String> messageErrorLoginAuth(AuthException e){

        return Map.of(MESSAGE, "Неправильный логин или пароль");
    }


    @ExceptionHandler(value = RegistrationErrorLoginExists.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ResponseBody
    public Map<String, String> messageErrorRegistration(RegistrationErrorLoginExists e){

        return Map.of(MESSAGE, "Такой логин уже существует");
    }

}
