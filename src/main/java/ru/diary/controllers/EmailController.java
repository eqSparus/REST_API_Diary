package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diary.models.UserAuth;
import ru.diary.services.DataService;
import ru.diary.services.EmailService;
import ru.diary.services.auth.ResetPassService;

import java.net.URI;

@CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class EmailController {

    EmailService emailService;
    DataService dataService;
    ResetPassService passService;

    @Autowired
    public EmailController(EmailService emailService, DataService dataService, ResetPassService passService) {
        this.emailService = emailService;
        this.dataService = dataService;
        this.passService = passService;
    }

    @GetMapping(path = "/confirm")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Void> confirm(
            @RequestParam("email") String email
    ) {
        emailService.updateUserActive(email);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000/confirm"))
                .build();
    }


    @PostMapping(path = "/reset_pass", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> resetPassword(
            @RequestBody UserAuth userAuth
    ) {
        var user = dataService.getUserByEmail(userAuth.getEmail());
        if (user.isPresent()) {
            passService.resetPassword(userAuth.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("");
    }

    @PostMapping(path = "/update_pass", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public String updatePassword(
            @RequestBody UserAuth user
    ) {
        dataService.updatePassword(user);
        return "";
    }


}