package ru.diary.models.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class LabelDto {
    String title;
    String color;

    @JsonCreator
    public LabelDto(
            @JsonProperty("title") String title,
            @JsonProperty("color") String color
    ) {
        this.title = title;
        this.color = color;
    }
}
