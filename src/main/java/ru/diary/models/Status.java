package ru.diary.models;

public enum Status {

    ACTIVE, IN_BASE, DELETE;

    public static Status getStatus(String status) {
        return Status.valueOf(status);
    }

}
