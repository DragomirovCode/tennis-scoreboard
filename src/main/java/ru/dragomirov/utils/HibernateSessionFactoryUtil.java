package ru.dragomirov.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.dragomirov.entities.Match;
import ru.dragomirov.entities.Player;

/**
 * HibernateSessionFactoryUtil используется для создания и управления сессиями Hibernate.
 * Этот класс использует HikariCP в качестве пула соединений для улучшения производительности.
 * Он также предоставляет методы для получения и закрытия SessionFactory.
 */
public class HibernateSessionFactoryUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Создание конфигурации HikariCP
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName("org.h2.Driver");
            hikariConfig.setJdbcUrl("jdbc:h2:mem:tennis-scoreboard-bd");
            hikariConfig.setUsername("sa");
            hikariConfig.setPassword("");
            hikariConfig.setMaximumPoolSize(1);

            // Создание источника данных HikariCP
            HikariDataSource dataSource = new HikariDataSource(hikariConfig);

            // Создание конфигурации Hibernate
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            configuration.addAnnotatedClass(Player.class);
            configuration.addAnnotatedClass(Match.class);

            // Использование источника данных HikariCP вместо стандартного подключения Hibernate
            configuration.getProperties().put("hibernate.connection.datasource", dataSource);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Ошибка при инициализации SessionFactory: " + ex.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
