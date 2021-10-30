package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.Label;
import ru.diary.models.form.LabelForm;
import ru.diary.services.DataService;

@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
        maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/crud/label")
public class LabelController {

    DataService<LabelForm, Label> labelService;
    TokenCreator creator;

    @Autowired
    public LabelController(DataService<LabelForm, Label> labelService, TokenCreator creator) {
        this.labelService = labelService;
        this.creator = creator;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public String createLabel(
            @RequestHeader("Authorization") String header,
            @RequestBody LabelForm labelForm
    ) {
        labelService.create(labelForm, creator.getLogin(header));
        return "";
    }

    @DeleteMapping(path = "/delete")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteDiary(
            @RequestParam("id") Long id
    ) {
        labelService.delete(id);
        return "";
    }

    @PutMapping(path = "/update")
    @ResponseStatus(code = HttpStatus.OK)
    public String updateDiary(
            @RequestParam("id") Long id,
            @RequestBody LabelForm labelForm
    ) {
        labelService.update(labelForm, id);
        return "";
    }


}
