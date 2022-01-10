package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diary.models.Record;
import ru.diary.models.dto.RecordDto;
import ru.diary.repositories.IRecordRepository;
import ru.diary.repositories.IUserRepository;
import ru.diary.services.IDataService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RecordService implements IDataService<RecordDto, Record> {

    IRecordRepository recordRepository;
    IUserRepository userRepository;

    @Autowired
    public RecordService(IRecordRepository recordRepository, IUserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Record create(RecordDto recordDto, String login) {

        var user = userRepository.findUserByEmail(login).orElseThrow(IllegalAccessError::new);

        var recordNew = Record.builder()
                .textBody(recordDto.getTextBody())
                .dateCreate(recordDto.getDateCreate())
                .isBookmark(recordDto.getBookmark())
                .diaryId(recordDto.getDiaryId())
                .labelId(recordDto.getLabelId())
                .userId(user.getId())
                .build();

        return recordRepository.create(recordNew).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public Record update(RecordDto recordDto, Long id) {
        return recordRepository.update(
                Record.builder()
                        .id(id)
                        .isBookmark(recordDto.getBookmark())
                        .build()
        ).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public void delete(Long id) {
        recordRepository.delete(id);
    }
}
