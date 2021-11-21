package ru.diary.repositories.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.diary.models.Record;
import ru.diary.repositories.RecordDao;

import javax.sql.rowset.spi.SyncResolver;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class UserRecordDao implements RecordDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRecordDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //language=SQL
    static String SQL_FIND_CREATE_RECORD =
            "SELECT * FROM records WHERE record_id = ?";

    //language=SQL
    static String SQL_FIND_ALL_RECORD =
            "SELECT * FROM records WHERE user_id = ?";

    //language=SQL
    static String SQL_INSERT_RECORD =
            "INSERT INTO records (text_body, date_creat, bookmark, diary_id, label_id, user_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    //language=SQL
    static String SQL_DELETE_RECORD =
            "DELETE FROM records WHERE record_id = ?";

    //language=SQL
    static String SQL_UPDATE_RECORD =
            "UPDATE records SET text_body = ?, bookmark = ?, label_id = ?";


    @Override
    public Optional<Record> create(Record record) {

        var key = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(SQL_INSERT_RECORD, new String[]{"record_id"});
            ps.setString(1, record.getTextBody());
            ps.setString(2, record.getDateCreate());
            ps.setBoolean(3, record.getIsBookmark());
            ps.setLong(4, record.getDiaryId());
            ps.setLong(5, record.getLabelId());
            ps.setLong(6, record.getUserId());
            return ps;
        }, key);


        return jdbcTemplate.queryForStream(SQL_FIND_CREATE_RECORD, recordMapper,
                key.getKey().longValue()).findAny();
    }

    @Override
    public void update(Record record, Long id) {
        jdbcTemplate.update(SQL_UPDATE_RECORD,
                record.getTextBody(),
                record.getIsBookmark(),
                record.getLabelId(),
                id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_RECORD, id);
    }

    @Override
    public List<Record> findAll(Long userId) {
        return jdbcTemplate.query(SQL_FIND_ALL_RECORD, recordMapper, userId);
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
