package ru.diary.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Data
@AllArgsConstructor
public class Label {
    Long id;
    String title;
    String color;
    Long userId;
}
