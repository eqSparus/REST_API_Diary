package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.form.UserAuth;
import ru.diary.services.DataAuthService;
import ru.diary.services.EmailService;
import ru.diary.services.auth.ResetPasswordService;

import java.net.URI;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class EmailController {

    private static final String MESSAGE = "message";
    EmailService emailService;
    DataAuthService dataService;
    ResetPasswordService passService;
    TokenCreator creator;

    @Autowired
    public EmailController(EmailService emailService, DataAuthService dataService,
                           ResetPasswordService passService, TokenCreator creator) {
        this.emailService = emailService;
        this.dataService = dataService;
        this.passService = passService;
        this.creator = creator;
    }

    @GetMapping(path = "/confirm")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Void> confirm(
            @RequestParam("email") String email
    ) {
        emailService.updateData(email);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000/confirm"))
                .build();
    }


    @PostMapping(path = "/reset_pass", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> resetPassword(
            @RequestBody UserAuth userAuth
    ) {

        if (passService.resetPassword(userAuth.getEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    MESSAGE, "Проверьте свою почту"
            ));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                MESSAGE, "Нет такого email адреса или он не активен"
        ));
    }

    @PostMapping(path = "/update_pass", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, String> updatePassword(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody UserAuth user
    ) {
        user.setEmail(creator.getLogin(token));
        dataService.updatePassword(user);
        return Map.of(
                MESSAGE, "Пароль изменен"
        );
    }
}
