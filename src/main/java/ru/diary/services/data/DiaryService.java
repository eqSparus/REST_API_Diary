package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diary.models.Diary;
import ru.diary.models.form.DiaryForm;
import ru.diary.repositories.DiaryDao;
import ru.diary.repositories.RecordDao;
import ru.diary.repositories.UserDao;
import ru.diary.services.DataService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class DiaryService implements DataService<DiaryForm, Diary> {

    UserDao userDao;
    DiaryDao diaryDao;
    RecordDao recordDao;

    @Autowired
    public DiaryService(UserDao userDao, DiaryDao diaryDao, RecordDao recordDao) {
        this.userDao = userDao;
        this.diaryDao = diaryDao;
        this.recordDao = recordDao;
    }

    @Override
    public Diary create(DiaryForm diaryForm, String login) {

        var user = userDao.findUserByEmail(login).orElseThrow(IllegalAccessError::new);

        var diary = Diary.builder()
                .title(diaryForm.getTitle())
                .userId(user.getId())
                .build();


        var newDiary = diaryDao.create(diary);
        return newDiary.orElseThrow(IllegalAccessError::new);
    }

    @Override
    public Diary update(DiaryForm diaryForm, Long id) {
        return diaryDao.update(
                Diary.builder()
                        .title(diaryForm.getTitle())
                        .build(),
                id
        ).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public void delete(Long id) {
        recordDao.deleteByDiary(id);
        diaryDao.delete(id);
    }
}
