package ru.diary.repositories.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.diary.models.Role;
import ru.diary.models.Status;
import ru.diary.models.User;
import ru.diary.repositories.UserDao;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class UserDaoAuth implements UserDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoAuth(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //language=SQL
    static String SQL_INSERT_USER =
            "INSERT INTO users(username, email, password, role, status) VALUES (?, ?, ?, ?, ?)";

    //language=SQL
    static String SQL_FIND_USER_BY_LOGIN =
            "SELECT * FROM users WHERE email = ?";

    //language=SQL
    static String SQL_UPDATE_STATUS_USER =
            "UPDATE users SET status = ? WHERE email = ?";

    @Override
    public void create(User user) {
        jdbcTemplate.update(SQL_INSERT_USER,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return jdbcTemplate.queryForStream(SQL_FIND_USER_BY_LOGIN, userMapper, email).findAny();
    }

    @Override
    public void updateStatusActive(String email) {
        jdbcTemplate.update(SQL_UPDATE_STATUS_USER, Status.ACTIVE.name(), email);
    }

    RowMapper<User> userMapper = (rs, rowNum) -> User.builder()
            .id(rs.getLong("user_id"))
            .username(rs.getString("username"))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .role(Role.getRole(rs.getString("role")))
            .status(Status.getStatus(rs.getString("status")))
            .build();
}
