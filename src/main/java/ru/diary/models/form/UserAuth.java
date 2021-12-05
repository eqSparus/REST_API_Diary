package ru.diary.models.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;


@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET}, maxAge = 3600)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserAuth {

    String username;
    String email;
    String password;

    @JsonCreator
    public UserAuth(
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
