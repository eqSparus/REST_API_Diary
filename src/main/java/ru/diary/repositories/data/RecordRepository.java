package ru.diary.repositories.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.diary.models.Record;
import ru.diary.repositories.IRecordRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class RecordRepository implements IRecordRepository {

    JdbcTemplate jdbcTemplate;
    Environment environment;

    @Autowired
    public RecordRepository(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }


    @Override
    public Optional<Record> create(Record record) {

        var sql = Objects.requireNonNull(environment.getProperty("record.insert"));

        return jdbcTemplate.queryForStream(sql, recordMapper,
                record.getTextBody(), record.getDateCreate(),
                record.getIsBookmark(), record.getDiaryId(),
                record.getLabelId(), record.getUserId()).findAny();
    }

    @Override
    public Optional<Record> update(Record record) {

        var sql = Objects.requireNonNull(environment.getProperty("record.updateById"));

        return jdbcTemplate.queryForStream(sql, recordMapper, record.getIsBookmark(), record.getId()).findAny();
    }

    @Override
    public void delete(Long id) {
        var sql = Objects.requireNonNull(environment.getProperty("record.deleteById"));
        jdbcTemplate.update(sql, id);
    }


    @Override
    public void deleteByDiary(Long id) {
        var sql = Objects.requireNonNull(environment.getProperty("record.deleteByDiaryId"));
        jdbcTemplate.update(sql, id);
    }


    @Override
    public List<Record> findAll(Long userId) {
        var sql = Objects.requireNonNull(environment.getProperty("record.selectAll"));
        return jdbcTemplate.query(sql, recordMapper, userId);
    }

    RowMapper<Record> recordMapper = (rs, rowNum) -> Record.builder()
            .id(rs.getLong("record_id"))
            .textBody(rs.getString("text_body"))
            .dateCreate(rs.getString("date_creat"))
            .isBookmark(rs.getBoolean("bookmark"))
            .diaryId(rs.getLong("diary_id"))
            .userId(rs.getLong("user_id"))
            .labelId(rs.getLong("label_id"))
            .build();


}
