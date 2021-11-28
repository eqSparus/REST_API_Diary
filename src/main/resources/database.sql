create table users
(
    user_id  serial
        constraint users_pk
            primary key,
    username varchar(50) not null,
    email    varchar(50) not null,
    password varchar(80) not null,
    status   varchar(10) not null,
    role     varchar(15) not null
);

alter table users
    owner to postgres;

create table labels
(
    label_id serial
        constraint labels_pk
            primary key,
    title    varchar(40) not null,
    color    varchar(8)  not null,
    user_id  serial
        constraint user_id
            references users
);

alter table labels
    owner to postgres;

create table diaries
(
    diary_id serial
        constraint diaries_pk
            primary key,
    title    varchar(40) not null,
    user_id  serial
        constraint user_id
            references users
);

alter table diaries
    owner to postgres;

create table records
(
    record_id  serial
        constraint records_pk
            primary key,
    text_body  text        not null,
    date_creat varchar(50) not null,
    bookmark   boolean     not null,
    diary_id   serial
        constraint diary_id
            references diaries,
    user_id    serial
        constraint user_id
            references users,
    label_id   integer
        constraint label_id
            references labels
            on update set null on delete set null
);

alter table records
    owner to postgres;