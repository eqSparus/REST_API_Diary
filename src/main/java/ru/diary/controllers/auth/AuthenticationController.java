package ru.diary.controllers.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diary.models.dto.UserAuth;
import ru.diary.services.IAuthenticationService;
import ru.diary.services.auth.AuthException;
import ru.diary.services.auth.RegistrationErrorLoginExists;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class AuthenticationController {

    static String MESSAGE = "message";
    static String TOKEN = "token";
    IAuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> loginUser(
            @RequestBody UserAuth user
    ) throws AuthException {
        return Map.of(TOKEN, authenticationService.loginUser(user));
    }


    @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Map<String, String> registrationUser(
            @RequestBody UserAuth user
    ) throws RegistrationErrorLoginExists {
        return Map.of(MESSAGE, authenticationService.registrationUser(user));
    }
}
