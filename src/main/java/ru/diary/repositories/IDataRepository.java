package ru.diary.repositories;

import java.util.List;
import java.util.Optional;

public interface IDataRepository<T> {

    Optional<T> create(T t);

    Optional<T> update(T t);

    void delete(Long id);

    List<T> findAll(Long userId);

}
