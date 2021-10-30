package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diary.models.Label;
import ru.diary.models.form.LabelForm;
import ru.diary.repositories.LabelDao;
import ru.diary.repositories.UserDao;
import ru.diary.services.DataService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LabelService implements DataService<LabelForm, Label> {

    LabelDao labelDao;
    UserDao userDao;

    @Autowired
    public LabelService(LabelDao labelDao, UserDao userDao) {
        this.labelDao = labelDao;
        this.userDao = userDao;
    }

    @Override
    public void create(LabelForm labelForm, String login) {

        var user = userDao.findUserByEmail(login).orElseThrow(IllegalAccessError::new);

        var label = Label.builder()
                .title(labelForm.getTitle())
                .color(labelForm.getColor())
                .userId(user.getId())
                .build();

        labelDao.create(label);
    }

    @Override
    public void update(LabelForm labelForm, Long id) {

        labelDao.update(
                Label.builder()
                        .title(labelForm.getTitle())
                        .color(labelForm.getColor())
                        .build(),
                id
        );

    }

    @Override
    public void delete(Long id) {
        labelDao.delete(id);
    }
}
