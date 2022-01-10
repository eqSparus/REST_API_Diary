package ru.diary.repositories.auth;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.diary.models.Role;
import ru.diary.models.Status;
import ru.diary.models.User;
import ru.diary.repositories.IUserRepository;

import java.util.Objects;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class UserAuthRepository implements IUserRepository {

    JdbcTemplate jdbcTemplate;
    Environment environment;

    @Autowired
    public UserAuthRepository(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }


    @Override
    public void create(User user) {
        var sql = Objects.requireNonNull(environment.getProperty("user.insert"));

        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        var sql = Objects.requireNonNull(environment.getProperty("user.findByEmail"));

        return jdbcTemplate.queryForStream(sql, userMapper, email).findAny();
    }

    @Override
    public void updateStatus(String email) {
        var sql = Objects.requireNonNull(environment.getProperty("user.updateStatusByEmail"));

        jdbcTemplate.update(sql, Status.ACTIVE.name(), email);
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
