package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.form.UserForm;
import ru.diary.repositories.UserDao;
import ru.diary.services.data.AllInformationService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET}, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class UserDataController {

    AllInformationService informationService;
    UserDao userDao;
    TokenCreator creator;

    @Autowired
    public UserDataController(AllInformationService informationService, UserDao userDao, TokenCreator creator) {
        this.informationService = informationService;
        this.userDao = userDao;
        this.creator = creator;
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> getData(
            @RequestHeader("Authorization") String header
    ) {

        var login = creator.getLogin(header);
        var user = userDao.findUserByEmail(login).orElseThrow(IllegalAccessError::new);
        var label = informationService.findAllLabel(user.getId());
        var diary = informationService.findAllDiary(user.getId());


        return Map.of(
                "user", new UserForm(user),
                "labels", label,
                "diary", diary
        );
    }


}
