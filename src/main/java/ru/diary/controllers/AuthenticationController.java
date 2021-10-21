package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.diary.models.UserAuth;
import ru.diary.services.EmailService;
import ru.diary.services.UserService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
public class AuthenticationController {

    UserService service;
    EmailService emailService;

    @Autowired
    public AuthenticationController(UserService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> loginUser(
            @RequestBody UserAuth user
    ) {

        var token = service.loginUser(user);

        if (token != null) {
            return ResponseEntity.ok().body(Map.of(
                    "token", token
            ));
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of());
    }

    //TODO Перенести emailService
    @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> registrationUser(
            @RequestBody UserAuth user
    ) {
        if (service.registrationUser(user)) {
            emailService.emailActiveMail(user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("");
    }
}
