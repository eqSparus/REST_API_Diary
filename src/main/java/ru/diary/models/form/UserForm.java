package ru.diary.models.form;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import ru.diary.models.User;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Value
public class UserForm {

    String username;
    String email;

    public UserForm(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
