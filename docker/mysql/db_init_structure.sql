USE myapp;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS quests;
DROP TABLE IF EXISTS stages;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS link_stage_question;
DROP TABLE IF EXISTS link_question_answer;


CREATE TABLE users
(
    id              BIGINT          NOT NULL  AUTO_INCREMENT
        PRIMARY KEY,
    display_name    VARCHAR(32)     NOT NULL,
    email           VARCHAR(128)    NOT NULL,
    first_name      VARCHAR(32)     NOT NULL,
    last_name       VARCHAR(32)     NULL,
    password        VARCHAR(128)    NOT NULL,
    dt_add          timestamp       DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_users_display_name
        UNIQUE (display_name),
    CONSTRAINT uk_users_email
        UNIQUE (email)
);

CREATE TABLE quests
(
    id              BIGINT          NOT NULL    AUTO_INCREMENT PRIMARY KEY,
    quest_name      VARCHAR(100)     NOT NULL,
    description     VARCHAR(8000)    NOT NULL,
    img_path        VARCHAR(255)    NULL,
    is_new          TINYINT(1)      NOT NULL    DEFAULT 0,
    dt_add          timestamp       NOT NULL    DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE stages
(
    id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quests_id       BIGINT          NOT NULL,
    description     VARCHAR(8000)    NOT NULL,
    img_path        VARCHAR(255)    NULL
);

CREATE TABLE questions
(
    id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quests_id       BIGINT          NOT NULL,
    description     VARCHAR(255)    NOT NULL
);

CREATE TABLE answers
(
    id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quests_id       BIGINT          NOT NULL,
    description     VARCHAR(255)    NOT NULL
);

CREATE TABLE link_stage_question
(
    id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stages_id       BIGINT          NOT NULL,
    questions_id    BIGINT          NOT NULL
);

CREATE TABLE link_question_answer
(
    id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    questions_id    BIGINT          NOT NULL,
    answers_id      BIGINT          NOT NULL
);