USE myapp;

DROP TABLE IF EXISTS users;
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
    quest_name      VARCHAR(32)     NOT NULL,
    description     VARCHAR(255)    NOT NULL,
    img_path        VARCHAR(255)    NULL,
    is_new          TINYINT(1)      NOT NULL    DEFAULT 0,
    dt_add          timestamp       NOT NULL    DEFAULT CURRENT_TIMESTAMP
);