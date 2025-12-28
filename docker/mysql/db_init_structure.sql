USE myapp;

DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS stages;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quests;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS sub_topics;
DROP TABLE IF EXISTS topics;


CREATE TABLE roles
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT uk_role_name
        UNIQUE (name)
);


CREATE TABLE users
(
    id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login_name   VARCHAR(32)  NOT NULL,
    display_name VARCHAR(32)  NOT NULL,
    email        VARCHAR(128) NOT NULL,
    first_name   VARCHAR(32)  NOT NULL,
    last_name    VARCHAR(32)  NULL,
    password     VARCHAR(128) NOT NULL,
    dt_add       timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_login_name
        UNIQUE (login_name),
    CONSTRAINT uk_users_email
        UNIQUE (email)
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_user_roles_roles
        FOREIGN KEY (role_id)
            REFERENCES roles (id),
    CONSTRAINT fk_user_roles_users
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);


CREATE TABLE quests
(
    id          BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quest_name  VARCHAR(100)  NOT NULL,
    description VARCHAR(8000) NOT NULL,
    img_path    VARCHAR(255)  NULL,
    is_new      TINYINT(1)    NOT NULL DEFAULT 0,
    dt_add      timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE questions
(
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE stages
(
    id            BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_quest_root BOOLEAN       NOT NULL DEFAULT 0,
    is_win        BOOLEAN       NOT NULL DEFAULT 0,
    is_defeat     BOOLEAN       NOT NULL DEFAULT 0,
    quests_id     BIGINT        NOT NULL,
    question_id   BIGINT        NULL,
    title         VARCHAR(255)  NOT NULL,
    description   VARCHAR(8000) NOT NULL,
    img_path      VARCHAR(255)  NULL,
    CONSTRAINT fk_stages_quests
        FOREIGN KEY (quests_id)
            REFERENCES quests (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_stages_questions
        FOREIGN KEY (question_id)
            REFERENCES questions (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE answers
(
    id            BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id   BIGINT       NOT NULL,
    next_stage_id BIGINT       NULL,
    description   VARCHAR(255) NOT NULL,
    CONSTRAINT fk_answers_questions
        FOREIGN KEY (question_id)
            REFERENCES questions (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_answers_next_stage_id
        FOREIGN KEY (next_stage_id)
            REFERENCES stages (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE topics
(
    id     BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    dt_add timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sub_topics
(
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    topic_id BIGINT       NOT NULL,
    name     VARCHAR(100) NOT NULL,
    dt_add   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (topic_id)
        REFERENCES topics (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE posts
(
    id           BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sub_topic_id BIGINT    NOT NULL,
    content      TEXT      NOT NULL,
    dt_add       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sub_topic_id)
        REFERENCES sub_topics (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE

);

