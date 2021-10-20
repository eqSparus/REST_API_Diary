package ru.diary.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@Builder
public class User {
    Long id;
    String username;
    String email;
    String password;
    Role role;
    Status status;
}
