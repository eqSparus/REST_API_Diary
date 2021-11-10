package ru.diary.repositories.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.diary.models.Label;
import ru.diary.repositories.LabelDao;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class UserLabelDao implements LabelDao {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserLabelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //language=SQL
    static String SQL_FIND_CREATE_LABEL =
            "SELECT * FROM labels WHERE label_id = ?";

    //language=SQL
    static String SQL_INSERT_LABEL =
            "INSERT INTO labels(title, color, create_at, user_id) VALUES (?, ?, ?, ?)";

    //language=SQL
    static String SQL_DELETE_LABEL =
            "DELETE FROM labels WHERE label_id = ?";

    //language=SQL
    static String SQL_UPDATE_LABEL =
            "UPDATE labels SET title = ?, color = ? WHERE label_id = ?";

    //language=SQL
    static String SQL_FIND_ALL_BY_USER =
            "SELECT * FROM labels WHERE user_id = ?";


    @Override
    public Optional<Label> create(Label label) {

        var key = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(SQL_INSERT_LABEL, new String[]{"label_id"});
            ps.setString(1, label.getTitle());
            ps.setString(2, label.getColor());
            ps.setString(3, label.getCreateDate());
            ps.setLong(4, label.getUserId());
            return ps;
        }, key);

        return jdbcTemplate.queryForStream(SQL_FIND_CREATE_LABEL, labelMapper,
                key.getKey().longValue()).findAny();
    }

    @Override
    public void update(Label label, Long id) {
        jdbcTemplate.update(SQL_UPDATE_LABEL, label.getTitle(), label.getColor(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_LABEL, id);
    }

    @Override
    public List<Label> findAll(Long userId) {
        return jdbcTemplate.query(SQL_FIND_ALL_BY_USER, labelMapper, userId);
    }

    RowMapper<Label> labelMapper = (rs, rowNum) -> Label.builder()
            .id(rs.getLong("label_id"))
            .title(rs.getString("title"))
            .color(rs.getString("color"))
            .createDate(rs.getString("create_at"))
            .userId(rs.getLong("user_id"))
            .build();
}
