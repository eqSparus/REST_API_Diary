package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diary.models.Record;
import ru.diary.models.form.RecordForm;
import ru.diary.repositories.RecordDao;
import ru.diary.repositories.UserDao;
import ru.diary.services.DataService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RecordService implements DataService<RecordForm, Record> {

    RecordDao recordDao;
    UserDao userDao;

    @Autowired
    public RecordService(RecordDao recordDao, UserDao userDao) {
        this.recordDao = recordDao;
        this.userDao = userDao;
    }

    @Override
    public Record create(RecordForm recordForm, String login) {

        var user = userDao.findUserByEmail(login).orElseThrow(IllegalAccessError::new);

        var record = Record.builder()
                .textBody(recordForm.getTextBody())
                .dateCreate(recordForm.getDateCreate())
                .isBookmark(recordForm.getBookmark())
                .diaryId(recordForm.getDiaryId())
                .labelId(recordForm.getLabelId())
                .userId(user.getId())
                .build();

        return recordDao.create(record).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public Record update(RecordForm recordForm, Long id) {
        return recordDao.update(
                Record.builder()
                        .isBookmark(recordForm.getBookmark())
                        .build(),
                id
        ).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public void delete(Long id) {
        recordDao.delete(id);
    }
}
