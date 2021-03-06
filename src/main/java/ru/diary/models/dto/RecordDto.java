package ru.diary.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RecordDto {

    String textBody;
    String dateCreate;
    Boolean bookmark;
    Long diaryId;
    Long labelId;

    @JsonCreator
    public RecordDto(
            @JsonProperty("textBody") String textBody,
            @JsonProperty("dataCreate") String dataCreate,
            @JsonProperty("bookmark") Boolean bookmark,
            @JsonProperty("diaryId") Long diaryId,
            @JsonProperty("labelId") Long labelId) {
        this.textBody = textBody;
        this.dateCreate = dataCreate;
        this.bookmark = bookmark;
        this.diaryId = diaryId;
        this.labelId = labelId;
    }
}
