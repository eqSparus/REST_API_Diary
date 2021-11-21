package ru.diary.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Value
@AllArgsConstructor
public class Record {
    Long id;
    String textBody;
    String dateCreate;
    Long labelId;
    Long diaryId;
    Long userId;
    Boolean isBookmark;
}
