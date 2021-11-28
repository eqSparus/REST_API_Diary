package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diary.models.form.UserAuth;
import ru.diary.services.UserService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class AuthenticationController {

    static String MESSAGE = "message";
    static String TOKEN = "token";
    UserService userService;

    @Autowired
    public AuthenticationController(UserService service) {
        this.userService = service;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> loginUser(
            @RequestBody UserAuth user
    ) {
        var token = userService.loginUser(user);

        return token.map(s -> ResponseEntity.ok().body(Map.of(
                TOKEN, s
        ))).orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                MESSAGE, "Неправильный логин или пароль"
        )));

    }


    @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> registrationUser(
            @RequestBody UserAuth user
    ) {
        if (userService.registrationUser(user)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    MESSAGE, "Пользователь создан"
            ));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                MESSAGE, "Такой пользователь уже существует"
        ));
    }
}
