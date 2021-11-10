package ru.diary.repositories;

import java.util.List;
import java.util.Optional;

public interface DataDao<T> {

    Optional<T> create(T t);

    void update(T t, Long id);

    void delete(Long id);

    List<T> findAll(Long userId);

}
