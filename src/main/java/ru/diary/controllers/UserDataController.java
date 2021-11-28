package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.form.UserForm;
import ru.diary.services.data.AllInformationService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET}, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class UserDataController {

    static String LABELS = "labels";
    static String DIARIES = "diaries";
    static String RECORDS = "records";
    static String USER = "user";

    AllInformationService informationService;
    TokenCreator creator;

    @Autowired
    public UserDataController(AllInformationService informationService, TokenCreator creator) {
        this.informationService = informationService;
        this.creator = creator;
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> getData(
            @RequestHeader("Authorization") String header
    ) {

        var email = creator.getLogin(header);
        var user = informationService.findUser(email);
        var labels = informationService.findAllLabel(user.getId());
        var diaries = informationService.findAllDiary(user.getId());
        var records = informationService.findAllRecord(user.getId());


        return Map.of(
                USER, new UserForm(user),
                LABELS, labels,
                DIARIES, diaries,
                RECORDS, records
        );
    }


}
