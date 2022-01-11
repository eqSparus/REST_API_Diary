package ru.diary.rest;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.JwtTokenProvider;
import ru.diary.models.dto.UserDto;
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
    JwtTokenProvider creator;

    @Autowired
    public UserDataController(AllInformationService informationService, JwtTokenProvider creator) {
        this.informationService = informationService;
        this.creator = creator;
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getData(
            @RequestHeader("Authorization") String header
    ) {

        var email = creator.getEmail(header);

        var user = informationService.findUser(email);
        var labels = informationService.findAllLabel(user.getId());
        var diaries = informationService.findAllDiary(user.getId());
        var records = informationService.findAllRecord(user.getId());


        return Map.of(
                USER, new UserDto(user),
                LABELS, labels,
                DIARIES, diaries,
                RECORDS, records
        );
    }
}
