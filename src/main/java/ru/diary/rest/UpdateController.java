package ru.diary.rest;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.JwtTokenProvider;
import ru.diary.models.dto.UserAuth;
import ru.diary.models.dto.UserDto;
import ru.diary.services.auth.exeption.RestPasswordException;
import ru.diary.services.auth.UpdateDataUser;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.PUT}, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/update")
public class UpdateController {

    static String MESSAGE = "message";
    UpdateDataUser updateDataUser;
    JwtTokenProvider creator;

    @Autowired
    public UpdateController(UpdateDataUser updateDataUser, JwtTokenProvider creator) {
        this.updateDataUser = updateDataUser;
        this.creator = creator;
    }

    @PutMapping(path = "/password", params = {"password", "newPassword"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> updatePassword(
            @RequestHeader("Authorization") String header,
            @RequestParam("password") String password,
            @RequestParam("newPassword") String newPassword
    ) throws RestPasswordException {

        return Map.of(MESSAGE, updateDataUser.updatePassword(creator.getEmail(header), password, newPassword));
    }

    @PutMapping(path = "/username")
    public UserDto updateName(
            @RequestHeader("Authorization") String header,
            @RequestBody UserAuth userAuth
    ) {
        return updateDataUser.updateName(userAuth, creator.getEmail(header));
    }

}
