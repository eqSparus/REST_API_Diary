package ru.diary.repositories.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.diary.models.User;
import ru.diary.repositories.DataDao;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class DataUserDao implements DataDao {

    JdbcTemplate template;

    @Autowired
    public DataUserDao(JdbcTemplate template) {
        this.template = template;
    }

    //language=SQL
    static String SQL_UPDATE_NAME =
            "UPDATE users SET name = ? WHERE email = ?";

    //language=SQL
    static String SQL_UPDATE_PASSWORD =
            "UPDATE users SET password = ? WHERE email = ?";


    @Override
    public void updateName(User user) {
        template.update(SQL_UPDATE_NAME, user.getUsername(), user.getEmail());
    }

    @Override
    public void updatePassword(User user) {
        System.out.println(user.getPassword());
        template.update(SQL_UPDATE_PASSWORD, user.getPassword(), user.getEmail());
    }
}
