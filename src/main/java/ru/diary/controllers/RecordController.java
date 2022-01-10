package ru.diary.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diary.configurations.security.jwt.JwtTokenProvider;
import ru.diary.models.Record;
import ru.diary.models.dto.RecordDto;
import ru.diary.services.IDataService;

@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT},
        maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping(path = "/crud/record")
public class RecordController {

    static Logger LOG = LoggerFactory.getLogger("test");

    IDataService<RecordDto, Record> recordService;
    JwtTokenProvider creator;

    @Autowired
    public RecordController(IDataService<RecordDto, Record> recordService, JwtTokenProvider creator) {
        this.recordService = recordService;
        this.creator = creator;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Record createRecord(
            @RequestHeader("Authorization") String header,
            @RequestBody RecordDto recordDto
    ) {
        LOG.info("New record {}", recordDto);
        return recordService.create(recordDto, creator.getEmail(header));
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<?> deleteRecord(
            @RequestParam("id") Long id
    ) {
        recordService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(params = "id", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Record updateRecord(
            @RequestParam("id") Long id,
            @RequestBody RecordDto recordDto
    ) {
        return recordService.update(recordDto, id);
    }
}
