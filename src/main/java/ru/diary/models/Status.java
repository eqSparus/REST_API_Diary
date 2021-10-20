package ru.diary.models;

public enum Status {

    ACTIVE, DELETE;

    public static Status getStatus(String status) {
        return Status.valueOf(status);
    }

}
