package ru.diary.rest;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.JwtTokenProvider;
import ru.diary.models.Diary;
import ru.diary.models.dto.DiaryDto;
import ru.diary.services.IDataService;

@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
        maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/crud/diary")
public class DiaryController {

    IDataService<DiaryDto, Diary> diaryService;
    JwtTokenProvider creator;

    @Autowired
    public DiaryController(IDataService<DiaryDto, Diary> diaryService, JwtTokenProvider creator) {
        this.diaryService = diaryService;
        this.creator = creator;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Diary createDiary(
            @RequestHeader("Authorization") String header,
            @RequestBody DiaryDto diaryDto
    ) {
        return diaryService.create(diaryDto, creator.getEmail(header));
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<HttpStatus> deleteDiary(
            @RequestParam("id") Long id
    ) {
        diaryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(params = "id", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Diary updateDiary(
            @RequestParam("id") Long id,
            @RequestBody DiaryDto diaryDto
    ) {
        return diaryService.update(diaryDto, id);
    }
}
