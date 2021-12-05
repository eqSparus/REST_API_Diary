package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.form.UserAuth;
import ru.diary.models.form.UserForm;
import ru.diary.services.auth.UpdateDataUser;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.PUT}, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/update")
public class UpdateController {

    static String MESSAGE = "message";
    UpdateDataUser updateDataUser;
    TokenCreator creator;

    @Autowired
    public UpdateController(UpdateDataUser updateDataUser, TokenCreator creator) {
        this.updateDataUser = updateDataUser;
        this.creator = creator;
    }

    @PutMapping(path = "/password")
    public ResponseEntity<Map<String, String>> updatePassword(
            @RequestHeader("Authorization") String header,
            @RequestParam("password") String password,
            @RequestParam("newPassword") String newPassword
    ) {

        if (updateDataUser.updatePassword(creator.getLogin(header), password, newPassword)) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    MESSAGE, "Пароль изменен"
            ));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                MESSAGE, "Не удалось изменить пароль"
        ));
    }

    @PutMapping(path = "/username")
    @ResponseStatus(code = HttpStatus.OK)
    public UserForm updateName(
            @RequestHeader("Authorization") String header,
            @RequestBody UserAuth userAuth
    ) {
        return updateDataUser.updateName(userAuth, creator.getLogin(header));
    }

}
