package ru.dragomirov.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConnectionUtils {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Ошибка при инициализации SessionFactory: " + ex.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
