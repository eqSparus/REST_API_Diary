package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.Diary;
import ru.diary.models.form.DiaryForm;
import ru.diary.services.DataService;

@CrossOrigin(origins = "http://localhost:3000/",
        maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/crud/diary")
public class DiaryController {

    DataService<DiaryForm, Diary> diaryService;
    TokenCreator creator;

    @Autowired
    public DiaryController(DataService<DiaryForm, Diary> diaryService, TokenCreator creator) {
        this.diaryService = diaryService;
        this.creator = creator;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Diary createDiary(
            @RequestHeader("Authorization") String header,
            @RequestBody DiaryForm diaryForm
    ) {
        return diaryService.create(diaryForm, creator.getLogin(header));
    }

    @DeleteMapping(path = "/delete")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteDiary(
            @RequestParam("id") Long id
    ) {
        diaryService.delete(id);
        return "";
    }

    @PutMapping(path = "/update")
    @ResponseStatus(code = HttpStatus.OK)
    public String updateDiary(
            @RequestParam("id") Long id,
            @RequestBody DiaryForm diaryForm
    ) {

        diaryService.update(diaryForm, id);

        return "";
    }

}
