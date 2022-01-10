-- Функция для создания и возвращения созданого дневника
CREATE FUNCTION create_diary_return(_title varchar(40), _id bigint)
    returns SETOF diaries
    language plpgsql as
$$
DECLARE
    id_insert_diary bigint;
BEGIN
    INSERT INTO diaries(title, user_id) VALUES (_title, _id) RETURNING diary_id INTO id_insert_diary;
    RETURN QUERY
        SELECT * FROM diaries WHERE diary_id = id_insert_diary;
end;
$$;


-- Функция для обновления и возвращения обновленого дневника
CREATE FUNCTION update_diary_by_id_return(_title varchar(40), _id bigint)
    returns SETOF diaries
    language plpgsql as
$$
BEGIN
    UPDATE diaries SET title = _title WHERE diary_id = _id;
    RETURN QUERY
        SELECT * FROM diaries WHERE diary_id = _id;
end;
$$;


-- Функция для создания и возвращения созданого ярылка
CREATE FUNCTION create_label_return(_title varchar(40), _color varchar(8), _id bigint)
    returns SETOF labels
    language plpgsql as
$$
DECLARE
    id_insert_label bigint;
BEGIN
    INSERT INTO labels(title, color, user_id) VALUES (_title, _color, _id) RETURNING label_id INTO id_insert_label;
    RETURN QUERY
        SELECT * FROM labels WHERE label_id = id_insert_label;
end;
$$;


-- Функция для обновления и возвращения обновленого ярылка
CREATE FUNCTION update_label_by_id_return(_title varchar(40), _color varchar(8), _id bigint)
    returns SETOF labels
    language plpgsql as
$$
BEGIN
    UPDATE labels SET title = _title, color=_color WHERE label_id = _id;
    RETURN QUERY
        SELECT * FROM labels WHERE label_id = _id;
end;
$$;


-- Функция для создания и возвращения созданой записи
CREATE FUNCTION create_record_return(_text_body text, _date_creat varchar(50), _bookmark bool, _diary_id bigint,
                                     _label_id bigint, _user_id bigint)
    returns SETOF records
    language plpgsql as
$$
DECLARE
    id_insert_record bigint;
BEGIN
    INSERT INTO records (text_body, date_creat, bookmark, diary_id, label_id, user_id)
    VALUES (_text_body, _date_creat, _bookmark, _diary_id, _label_id, _user_id)
    RETURNING record_id INTO id_insert_record;
    RETURN QUERY
        SELECT * FROM records WHERE record_id = id_insert_record;
end;
$$;


-- Функция для обновления и возвращения обновленой записи
CREATE FUNCTION update_record_by_id_return(_bookmark bool, _id bigint)
    returns SETOF records
    language plpgsql as
$$
BEGIN
    UPDATE records SET bookmark = _bookmark WHERE record_id = _id;
    RETURN QUERY
        SELECT * FROM records WHERE record_id = _id;
end;
$$;