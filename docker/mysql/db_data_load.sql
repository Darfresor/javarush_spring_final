USE
myapp;

DELETE
FROM users;

INSERT INTO users
    (ID, DISPLAY_NAME, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, STARTPOINT)
VALUES(1,'admin','admin@gmail.com','admin','admin','admin',CURRENT_TIMESTAMP)