package ru.diary.rest;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.JwtTokenProvider;
import ru.diary.models.dto.UserAuth;
import ru.diary.services.IDataAuthService;
import ru.diary.services.IEmailService;
import ru.diary.services.auth.ResetPasswordService;
import ru.diary.services.auth.exeption.RestPasswordEmailException;

import java.net.URI;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.POST,RequestMethod.GET}, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class EmailController {

    static String MESSAGE = "message";
    IEmailService emailService;
    IDataAuthService dataService;
    ResetPasswordService passService;
    JwtTokenProvider creator;

    @Autowired
    public EmailController(IEmailService emailService, IDataAuthService dataService,
                           ResetPasswordService passService, JwtTokenProvider creator) {
        this.emailService = emailService;
        this.dataService = dataService;
        this.passService = passService;
        this.creator = creator;
    }

    @GetMapping(path = "/confirm", params = "email")
    public ResponseEntity<Void> confirm(
            @RequestParam("email") String email
    ) {
        emailService.updateData(email);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000/confirm"))
                .build();
    }


    @PostMapping(path = "/reset_pass", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> resetPassword(
            @RequestBody UserAuth userAuth
    ) throws RestPasswordEmailException {
        return Map.of(MESSAGE, passService.resetPassword(userAuth.getEmail()));
    }

    @PostMapping(path = "/update_pass", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> updatePassword(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody UserAuth user
    ) {
        user.setEmail(creator.getEmail(token));
        dataService.resetPassword(user);
        return Map.of(MESSAGE, "Пароль изменен");
    }
}
