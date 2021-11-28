package ru.diary.repositories.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.diary.models.Diary;
import ru.diary.repositories.DiaryDao;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class UserDiaryDao implements DiaryDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDiaryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //language=SQL
    static String SQL_FIND_UP_CR_DIARY =
            "SELECT * FROM diaries WHERE diary_id = ?";


    //language=SQL
    static String SQL_INSERT_DIARY =
            "INSERT INTO diaries(title, user_id) VALUES (?, ?)";

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
    public Optional<Diary> create(Diary diary) {
        var key = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(SQL_INSERT_DIARY, new String[]{"diary_id"});
            ps.setString(1, diary.getTitle());
            ps.setLong(2, diary.getUserId());
            return ps;
        }, key);

        return jdbcTemplate.queryForStream(SQL_FIND_UP_CR_DIARY, diaryMapper,
                key.getKey().longValue()).findAny();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_DIARY, id);
    }

    @Override
    public Optional<Diary> update(Diary diary, Long id) {
        var key = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(SQL_UPDATE_DIARY, new String[]{"diary_id"});
            ps.setString(1, diary.getTitle());
            ps.setLong(2, id);
            return ps;
        }, key);
        return jdbcTemplate.queryForStream(SQL_FIND_UP_CR_DIARY, diaryMapper,
                key.getKey().longValue()).findAny();
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
