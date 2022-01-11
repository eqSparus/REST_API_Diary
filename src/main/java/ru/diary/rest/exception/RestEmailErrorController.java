package ru.diary.rest.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.diary.rest.EmailController;
import ru.diary.services.auth.exeption.RestPasswordEmailException;
import ru.diary.services.auth.exeption.RestPasswordException;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ControllerAdvice(basePackageClasses = EmailController.class)
public class RestEmailErrorController {

    static String MESSAGE = "message";

    @ExceptionHandler(value = RestPasswordEmailException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ResponseBody
    public Map<String, String> getMessageErrorEmailException(RestPasswordEmailException e){

        return Map.of(MESSAGE, "Нет такого email адреса или он не активен");
    }


    @ExceptionHandler(value = RestPasswordException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ResponseBody
    public Map<String, String> getMessageErrorPasswordRestException(RestPasswordException e){

        return Map.of(MESSAGE, "Не удалось изменить пароль");
    }



}
