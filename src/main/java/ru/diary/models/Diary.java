package ru.diary.models;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Diary {
    Long id;
    String title;
    String description;
    String createDate;
    Long userId;
}
