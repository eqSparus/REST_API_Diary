package ru.diary.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DiaryDto {

    String title;

    @JsonCreator
    public DiaryDto(
            @JsonProperty("title") String title
    ) {
        this.title = title;
    }
}
