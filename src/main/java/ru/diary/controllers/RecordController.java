package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.TokenCreator;
import ru.diary.models.Record;
import ru.diary.models.form.RecordForm;
import ru.diary.services.DataService;

@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
        maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/crud/record")
public class RecordController {

    static Logger LOG = LoggerFactory.getLogger("test");

    DataService<RecordForm, Record> recordService;
    TokenCreator creator;

    @Autowired
    public RecordController(DataService<RecordForm, Record> recordService, TokenCreator creator) {
        this.recordService = recordService;
        this.creator = creator;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Record createRecord(
            @RequestHeader("Authorization") String header,
            @RequestBody RecordForm recordForm
    ) {
        LOG.info("New record {}", recordForm);
        return recordService.create(recordForm, creator.getLogin(header));
    }

    @DeleteMapping(path = "/delete")
    @ResponseStatus(code = HttpStatus.OK)
    public String deleteRecord(
            @RequestParam("id") Long id
    ) {
        recordService.delete(id);
        return "";
    }

    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Record updateRecord(
            @RequestParam("id") Long id,
            @RequestBody RecordForm recordForm
    ) {
        return recordService.update(recordForm, id);
    }
}
