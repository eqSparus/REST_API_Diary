package ru.diary.repositories;

import java.util.List;

public interface DataDao<T> {

    void create(T t);

    void update(T t, Long id);

    void delete(Long id);

    List<T> findAll(Long userId);

}
