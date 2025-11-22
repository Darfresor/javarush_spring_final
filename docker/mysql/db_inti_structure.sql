USE myapp;

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    ID              bigint          not null  AUTO_INCREMENT PRIMARY KEY,
    DISPLAY_NAME    varchar(32)     not null,
    EMAIL           varchar(128)    not null,
    FIRST_NAME      varchar(32)     not null,
    LAST_NAME       varchar(32)     null,
    PASSWORD        varchar(128)    not null,
    STARTPOINT      timestamp
);