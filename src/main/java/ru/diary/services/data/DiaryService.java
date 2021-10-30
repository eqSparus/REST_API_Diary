package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diary.models.Diary;
import ru.diary.models.form.DiaryForm;
import ru.diary.repositories.DiaryDao;
import ru.diary.repositories.UserDao;
import ru.diary.services.DataService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class DiaryService implements DataService<DiaryForm,Diary> {

    UserDao userDao;
    DiaryDao diaryDao;

    @Autowired
    public DiaryService(UserDao userDao, DiaryDao diaryDao) {
        this.userDao = userDao;
        this.diaryDao = diaryDao;
    }

    @Override
    public void create(DiaryForm diaryForm, String login) {

        var user = userDao.findUserByEmail(login).orElseThrow(IllegalAccessError::new);

        var diary = Diary.builder()
                .title(diaryForm.getTitle())
                .userId(user.getId())
                .build();


        diaryDao.create(diary);
    }

    @Override
    public void update(DiaryForm diaryForm, Long id) {
        diaryDao.update(
                Diary.builder()
                        .title(diaryForm.getTitle())
                        .build(),
                id
        );
    }

    @Override
    public void delete(Long id) {
        diaryDao.delete(id);
    }
}
