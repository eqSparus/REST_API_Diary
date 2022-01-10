package ru.diary.repositories.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.diary.models.User;
import ru.diary.repositories.IPersonalDataRepository;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class PersonalDataRepository implements IPersonalDataRepository {

    JdbcTemplate template;
    Environment environment;

    @Autowired
    public PersonalDataRepository(JdbcTemplate template, Environment environment) {
        this.template = template;
        this.environment = environment;
    }

    @Override
    public void updateName(User user) {
        var sql = Objects.requireNonNull(environment.getProperty("user.updateUsernameByEmail"));
        template.update(sql, user.getUsername(), user.getEmail());
    }

    @Override
    public void updatePassword(User user) {
        var sql = Objects.requireNonNull(environment.getProperty("user.updatePasswordByEmail"));
        template.update(sql, user.getPassword(), user.getEmail());
    }
}
