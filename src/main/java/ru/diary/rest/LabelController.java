package ru.diary.rest;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.JwtTokenProvider;
import ru.diary.models.Label;
import ru.diary.models.dto.LabelDto;
import ru.diary.services.IDataService;

@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
        maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/crud/label")
public class LabelController {

    IDataService<LabelDto, Label> labelService;
    JwtTokenProvider creator;

    @Autowired
    public LabelController(IDataService<LabelDto, Label> labelService, JwtTokenProvider creator) {
        this.labelService = labelService;
        this.creator = creator;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Label createLabel(
            @RequestHeader("Authorization") String header,
            @RequestBody LabelDto labelDto
    ) {
        return labelService.create(labelDto, creator.getEmail(header));
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<HttpStatus> deleteDiary(
            @RequestParam("id") Long id
    ) {
        labelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(params = "id", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Label updateDiary(
            @RequestParam("id") Long id,
            @RequestBody LabelDto labelDto
    ) {
        return labelService.update(labelDto, id);
    }
}
