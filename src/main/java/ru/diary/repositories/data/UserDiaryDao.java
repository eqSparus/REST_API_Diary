package ru.diary.repositories.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.diary.models.Diary;
import ru.diary.repositories.DiaryDao;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class UserDiaryDao implements DiaryDao {

    JdbcTemplate jdbcTemplate;
    static Logger LOG = LoggerFactory.getLogger("test");


    @Autowired
    public UserDiaryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //language=SQL
    static String SQL_INSERT_DIARY =
            "INSERT INTO diaries( title, user_id) VALUES (?, ?)";

    //language=SQL
    static String SQL_DELETE_DIARY =
            "DELETE FROM diaries WHERE diary_id = ?";

    //language=SQL
    static String SQL_UPDATE_DIARY =
            "UPDATE diaries SET title = ? WHERE diary_id = ?";

    //language=SQL
    static String SQL_FIND_ALL_DIARY_BY_USER =
            "SELECT * FROM diaries WHERE user_id = ?";

    @Override
    public void create(Diary diary) {
        LOG.info("{},{}", diary.getTitle(), diary.getUserId());
        jdbcTemplate.update(SQL_INSERT_DIARY, diary.getTitle(), diary.getUserId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_DIARY, id);
    }

    @Override
    public void update(Diary diary, Long id) {
        jdbcTemplate.update(SQL_UPDATE_DIARY, diary.getTitle(), id);
    }

    @Override
    public List<Diary> findAll(Long userId) {
        return jdbcTemplate.query(SQL_FIND_ALL_DIARY_BY_USER, diaryMapper, userId);
    }

    RowMapper<Diary> diaryMapper = (rs, rowNum) -> Diary.builder()
            .id(rs.getLong("diary_id"))
            .title(rs.getString("title"))
            .userId(rs.getLong("user_id"))
            .build();
}
