package ru.diary.rest.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diary.rest.AuthenticationController;
import ru.diary.services.auth.exeption.AuthException;
import ru.diary.services.auth.exeption.RegistrationErrorLoginExistsException;

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


    @ExceptionHandler(value = RegistrationErrorLoginExistsException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ResponseBody
    public Map<String, String> messageErrorRegistration(RegistrationErrorLoginExistsException e){

        return Map.of(MESSAGE, "Такой логин уже существует");
    }

}
