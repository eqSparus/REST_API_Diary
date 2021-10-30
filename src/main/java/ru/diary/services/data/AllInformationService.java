package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diary.models.Diary;
import ru.diary.models.Label;
import ru.diary.repositories.DiaryDao;
import ru.diary.repositories.LabelDao;
import ru.diary.repositories.UserDao;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AllInformationService {

    final LabelDao labelDao;
    final DiaryDao diaryDao;
    final UserDao userDao;

    @Autowired
    public AllInformationService(LabelDao labelDao, DiaryDao diaryDao, UserDao userDao) {
        this.labelDao = labelDao;
        this.diaryDao = diaryDao;
        this.userDao = userDao;
    }

    public List<Label> findAllLabel(Long userId) {
        return labelDao.findAll(userId);
    }

    public List<Diary> findAllDiary(Long userId) {
        return diaryDao.findAll(userId);
    }
}
