USE myapp;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS stages;
DROP TABLE IF EXISTS quests;




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
    quest_name      VARCHAR(100)    NOT NULL,
    description     VARCHAR(8000)   NOT NULL,
    img_path        VARCHAR(255)    NULL,
    is_new          TINYINT(1)      NOT NULL    DEFAULT 0,
    dt_add          timestamp       NOT NULL    DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE stages
(
    id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    quests_id       BIGINT          NOT NULL,
    title           VARCHAR(255)    NOT NULL,
    description     VARCHAR(8000)   NOT NULL,
    img_path        VARCHAR(255)    NULL,
    CONSTRAINT fk_stages_quests
        FOREIGN KEY (quests_id)
            REFERENCES quests(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE questions
(
    id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stage_id        BIGINT          NOT NULL,
    description     VARCHAR(255)    NOT NULL,
    CONSTRAINT uk_questions_stage_id
        UNIQUE (stage_id),
    CONSTRAINT fk_questions_stages
        FOREIGN KEY (stage_id)
            REFERENCES stages(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE answers
(
    id              BIGINT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question_id     BIGINT          NOT NULL,
    next_stage_id   BIGINT          NULL,
    description     VARCHAR(255)    NOT NULL,
    CONSTRAINT fk_answers_questions
        FOREIGN KEY (question_id)
            REFERENCES questions(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_answers_next_stage_id
        FOREIGN KEY (next_stage_id)
            REFERENCES stages(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

