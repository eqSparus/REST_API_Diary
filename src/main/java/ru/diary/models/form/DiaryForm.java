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

    @JsonCreator
    public DiaryForm(
            @JsonProperty("title") String title
    ) {
        this.title = title;
    }
}
