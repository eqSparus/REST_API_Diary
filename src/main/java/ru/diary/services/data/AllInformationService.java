package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diary.models.Diary;
import ru.diary.models.Label;
import ru.diary.models.Record;
import ru.diary.models.User;
import ru.diary.repositories.DiaryDao;
import ru.diary.repositories.LabelDao;
import ru.diary.repositories.RecordDao;
import ru.diary.repositories.UserDao;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AllInformationService {

    final LabelDao labelDao;
    final DiaryDao diaryDao;
    final UserDao userDao;
    final RecordDao recordDao;

    @Autowired
    public AllInformationService(LabelDao labelDao, DiaryDao diaryDao, UserDao userDao, RecordDao recordDao) {
        this.labelDao = labelDao;
        this.diaryDao = diaryDao;
        this.userDao = userDao;
        this.recordDao = recordDao;
    }

    public List<Label> findAllLabel(Long userId) {
        return labelDao.findAll(userId);
    }

    public List<Diary> findAllDiary(Long userId) {
        return diaryDao.findAll(userId);
    }

    public User findUser(String email) {
        return userDao.findUserByEmail(email).orElseThrow(IllegalAccessError::new);
    }

    public List<Record> findAllRecord(Long userId) {
        return recordDao.findAll(userId);
    }
}
