package ru.diary.models.dto;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import ru.diary.models.User;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Value
public class UserDto {

    String username;
    String email;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
