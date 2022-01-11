package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diary.models.Diary;
import ru.diary.models.dto.DiaryDto;
import ru.diary.repositories.IDiaryRepository;
import ru.diary.repositories.IRecordRepository;
import ru.diary.repositories.IUserRepository;
import ru.diary.services.IDataService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Transactional
public class DiaryService implements IDataService<DiaryDto, Diary> {

    IUserRepository userRepository;
    IDiaryRepository diaryRepository;
    IRecordRepository recordRepository;

    @Autowired
    public DiaryService(IUserRepository userRepository,
                        IDiaryRepository diaryRepository,
                        IRecordRepository recordRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        this.recordRepository = recordRepository;
    }

    @Override
    public Diary create(DiaryDto diaryDto, String login) {

        var user = userRepository.findUserByEmail(login).orElseThrow(IllegalAccessError::new);

        var diary = Diary.builder()
                .title(diaryDto.getTitle())
                .userId(user.getId())
                .build();


        var newDiary = diaryRepository.create(diary);
        return newDiary.orElseThrow(IllegalAccessError::new);
    }

    @Override
    public Diary update(DiaryDto diaryDto, Long id) {
        return diaryRepository.update(
                Diary.builder()
                        .id(id)
                        .title(diaryDto.getTitle())
                        .build()
        ).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public void delete(Long id) {
        recordRepository.deleteByDiary(id);
        diaryRepository.delete(id);
    }
}
