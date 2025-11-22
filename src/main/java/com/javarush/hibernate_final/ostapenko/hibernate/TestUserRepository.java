package com.javarush.hibernate_final.ostapenko.hibernate;

import com.javarush.hibernate_final.ostapenko.hibernate.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.List;
import java.util.Properties;

public class TestUserRepository {
    private SessionFactory sessionFactory;

    public void init(){
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/myapp");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.STATEMENT_BATCH_SIZE, "100");

        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addProperties(properties)
                .buildSessionFactory();
    }

    public TestUserRepository() {
        init();
    }

    public List<User> getAllUsers(){
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from User", User.class).list();
        }
    }
    public void addUser(User user){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }
}
