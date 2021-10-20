package ru.diary.models;

public enum Role {
    USER,ADMIN;

    public static Role getRole(String role){
        return Role.valueOf(role);
    }
}
