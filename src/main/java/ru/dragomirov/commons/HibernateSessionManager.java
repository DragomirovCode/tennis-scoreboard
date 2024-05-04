package ru.dragomirov.commons;
import org.hibernate.Session;
import ru.dragomirov.utils.HibernateSessionFactoryUtil;

public class HibernateSessionManager {

    // Функциональный интерфейс для выполнения операции с сеансом Hibernate
    public interface SessionQuery<T> {
        T execute(Session session);
    }

    // Функциональный интерфейс для выполнения транзакционных операций
    public interface TransactionOperation {
        void execute(Session session);
    }

    // Метод для выполнения запроса к базе данных с использованием сеанса Hibernate
    public static <T> T performSessionQuery(SessionQuery<T> sessionQuery, String errorMessage) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return sessionQuery.execute(session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(errorMessage, e);
        }
    }

    // Метод для выполнения транзакции с использованием сеанса Hibernate
    public static void performTransaction(TransactionOperation transactionOperation) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        transactionOperation.execute(session);
        session.getTransaction().commit();
        session.close();
    }
}
