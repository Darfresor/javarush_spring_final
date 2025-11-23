package com.javarush.hibernate_final.ostapenko.hibernate;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.Quest;
import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.User;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.List;

public class Tests {
    public static void main(String[] args) throws SQLException {
        //User user1 = new User("user", "user@gmail.com", "firstname", "querty");
       // System.out.println(user1);

        /*
        Connection connection  = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/myapp",
                "root", "root");
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM USERS");
        while (results.next()) {
            Integer id = results.getInt(1);
            String name = results.getString(2);
            System.out.println(results.getRow() + ". " + id + "\t"+ name);
        }
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++)
        {
            String name = metaData.getColumnName(column);
            String className = metaData.getColumnClassName(column);
            String typeName = metaData.getColumnTypeName(column);
            int type = metaData.getColumnType(column);

            System.out.println(name + "\t" + className + "\t" + typeName + "\t" + type);
        }
        ResultSet results2 = statement.executeQuery("SELECT Count(*) FROM USERS");
        results2.next();
        int count = results2.getInt(1);
        System.out.println(count);

        String sql = "SELECT * FROM USERS WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1,1L);
        ResultSet results3 = preparedStatement.executeQuery();
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet crs = factory.createCachedRowSet();
        crs.populate(results3);	// Используем ResultSet для заполнения

        connection.close();		// Закрываем соединение


        while (crs.next()) {
            Integer id = crs.getInt(1);
            String name = crs.getString(2);
            System.out.println(crs.getRow() + ". " + id + "\t"+ name);
        }
        */
        /*
        TestUserRepository repo = new TestUserRepository();
        List<User> users =  repo.getAllUsers();
        System.out.println(users);

        User user1 = new User("user", "user@gmail.com", "firstname", "querty");
        repo.addUser(user1);

        users =  repo.getAllUsers();
        System.out.println(users);
        */
        Quest quest = new Quest(1L,"тестовый квест","какое-то описание");
        System.out.println(quest.getQuestName());


    }
}
