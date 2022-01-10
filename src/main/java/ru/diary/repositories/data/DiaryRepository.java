package ru.diary.repositories.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.diary.models.Diary;
import ru.diary.repositories.IDiaryRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class DiaryRepository implements IDiaryRepository {

    JdbcTemplate jdbcTemplate;
    Environment environment;

    @Autowired
    public DiaryRepository(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    @Override
    public Optional<Diary> create(Diary diary) {

        var sql = Objects.requireNonNull(environment.getProperty("diary.insert"));

        return jdbcTemplate.queryForStream(sql, diaryMapper, diary.getTitle(), diary.getUserId()).findAny();
    }

    @Override
    public void delete(Long id) {
        var sql = Objects.requireNonNull(environment.getProperty("diary.deleteById"));
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Diary> update(Diary diary) {

        var sql = Objects.requireNonNull(environment.getProperty("diary.updateById"));

        return jdbcTemplate.queryForStream(sql,diaryMapper,diary.getTitle(),diary.getId()).findAny();
    }

    @Override
    public List<Diary> findAll(Long userId) {
        var sql = Objects.requireNonNull(environment.getProperty("diary.selectAll"));
        return jdbcTemplate.query(sql, diaryMapper, userId);
    }

    RowMapper<Diary> diaryMapper = (rs, rowNum) -> Diary.builder()
            .id(rs.getLong("diary_id"))
            .title(rs.getString("title"))
            .userId(rs.getLong("user_id"))
            .build();
}
