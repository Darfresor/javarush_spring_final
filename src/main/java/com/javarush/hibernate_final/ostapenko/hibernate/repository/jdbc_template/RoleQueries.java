package com.javarush.hibernate_final.ostapenko.hibernate.repository.jdbc_template;

public class RoleQueries {

    private RoleQueries(){}

    public static final String  FIND_BY_ID = """
            SELECT * FROM roles WHERE id = ?
            """;
    public static final String  FIND_BY_NAME = """
            SELECT * FROM roles WHERE name = ?
            """;
    public static final String  FIND_ALL = """
            SELECT * FROM roles
            """;
    public static final String SAVE_STEP1_INSERT = """
            INSERT INTO roles (name) VALUES (?)
            """;
    public static final String SAVE_STEP1_UPDATE = """
            UPDATE roles SET name = ? WHERE id = ?
            """;
    public static final String SAVE_STEP2_GET_LAST_INSERT_ID = """
            SELECT LAST_INSERT_ID()
            """;
    public static final String DELETE_BY_ID = """
            DELETE FROM roles WHERE id = ?
            """;
    public static final String COUNT_BY_ID = """
            SELECT COUNT(*) FROM roles WHERE id = ?
            """;
    public static final String COUNT_ALL = """
            SELECT COUNT(*) FROM roles
            """;
}
