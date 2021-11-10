package ru.diary.models.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DiaryForm {

    String title;
    String description;
    String createDate;

    @JsonCreator
    public DiaryForm(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("createAt") String createDate
    ) {
        this.title = title;
        this.description = description;
        this.createDate = createDate;
    }
}
