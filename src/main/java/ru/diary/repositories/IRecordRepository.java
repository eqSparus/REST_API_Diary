package ru.diary.repositories;

import ru.diary.models.Record;

public interface IRecordRepository extends IDataRepository<Record> {

    void deleteByDiary(Long id);

}
