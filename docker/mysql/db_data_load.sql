USE
    myapp;

DELETE
FROM users;

INSERT INTO users
    (id, display_name, email, first_name, last_name, password, dt_add)
VALUES (1, 'admin', 'admin@gmail.com', 'admin', 'admin', 'admin', CURRENT_TIMESTAMP);

DELETE
FROM quests;

INSERT INTO quests
    (id, quest_name, description, img_path)
VALUES (1, 'Квест1', 'тут будет описание','quests/quest1/title.jpg'),
       (2, 'Квест2', 'тут будет описание', null),
       (3, 'Квест3', 'тут будет описание', null),
       (4, 'Квест4', 'тут будет описание', null),
       (5, 'Квест5', 'тут будет описание', null),
       (6, 'Квест6', 'тут будет описание', null),
       (7, 'Квест7', 'тут будет описание', null),
       (8, 'Квест8', 'тут будет описание', null),
       (9, 'Квест9', 'тут будет описание', null),
       (10, 'Квест10', 'тут будет описание', null),
       (11, 'Квест11', 'тут будет описание', null);