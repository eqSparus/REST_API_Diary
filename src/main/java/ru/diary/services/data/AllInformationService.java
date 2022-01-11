package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diary.models.Diary;
import ru.diary.models.Label;
import ru.diary.models.Record;
import ru.diary.models.User;
import ru.diary.repositories.IDiaryRepository;
import ru.diary.repositories.ILabelRepository;
import ru.diary.repositories.IRecordRepository;
import ru.diary.repositories.IUserRepository;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Transactional
public class AllInformationService {

    final ILabelRepository labelRepository;
    final IDiaryRepository diaryRepository;
    final IUserRepository userRepository;
    final IRecordRepository recordRepository;

    @Autowired
    public AllInformationService(ILabelRepository labelRepository,
                                 IDiaryRepository diaryRepository,
                                 IUserRepository userRepository,
                                 IRecordRepository recordRepository) {
        this.labelRepository = labelRepository;
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
    }

    public List<Label> findAllLabel(Long userId) {
        return labelRepository.findAll(userId);
    }

    public List<Diary> findAllDiary(Long userId) {
        return diaryRepository.findAll(userId);
    }

    public User findUser(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(IllegalArgumentException::new);
    }

    public List<Record> findAllRecord(Long userId) {
        return recordRepository.findAll(userId);
    }
}
