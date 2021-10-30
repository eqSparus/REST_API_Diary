package ru.diary.services;

public interface DataService<T,R> {

    void create(T t, String login);

    void update(T t, Long id);

    void delete(Long id);

}
