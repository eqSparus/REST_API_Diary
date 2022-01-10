package ru.diary.repositories.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.diary.models.Label;
import ru.diary.repositories.ILabelRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class LabelRepository implements ILabelRepository {

    JdbcTemplate jdbcTemplate;
    Environment environment;

    @Autowired
    public LabelRepository(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    @Override
    public Optional<Label> create(Label label) {

        var sql = Objects.requireNonNull(environment.getProperty("label.insert"));

        return jdbcTemplate.queryForStream(sql, labelMapper,
                label.getTitle(), label.getColor(), label.getUserId()).findAny();
    }

    @Override
    public Optional<Label> update(Label label) {

        var sql = Objects.requireNonNull(environment.getProperty("label.updateById"));

        return jdbcTemplate.queryForStream(sql, labelMapper,
                label.getTitle(), label.getColor(), label.getId()).findAny();
    }

    @Override
    public void delete(Long id) {
        var sql = Objects.requireNonNull(environment.getProperty("label.deleteById"));
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Label> findAll(Long userId) {
        var sql = Objects.requireNonNull(environment.getProperty("label.selectAll"));
        return jdbcTemplate.query(sql, labelMapper, userId);
    }

    RowMapper<Label> labelMapper = (rs, rowNum) -> Label.builder()
            .id(rs.getLong("label_id"))
            .title(rs.getString("title"))
            .color(rs.getString("color"))
            .userId(rs.getLong("user_id"))
            .build();
}
