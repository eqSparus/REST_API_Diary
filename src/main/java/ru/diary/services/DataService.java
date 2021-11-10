package ru.diary.services;

public interface DataService<T,R> {

    R create(T t, String login);

    void update(T t, Long id);

    void delete(Long id);

}
