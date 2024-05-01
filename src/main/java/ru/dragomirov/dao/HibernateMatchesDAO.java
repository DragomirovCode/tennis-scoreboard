package ru.dragomirov.dao;

import ru.dragomirov.commons.HibernateSessionManager;
import ru.dragomirov.entities.Matches;
import ru.dragomirov.repositories.MatchesRepository;

import java.util.List;

public class HibernateMatchesDAO implements MatchesRepository {
    private PlayersDAO playersDAO = new PlayersDAO();

    @Override
    public List<Matches> findAll(int page, int pageSize) {
        return HibernateSessionManager.performSessionQuery(session -> session.createQuery("FROM Matches", Matches.class)
                        .setFirstResult((page - 1) * pageSize)
                        .setMaxResults(pageSize)
                        .list(),
                "Произошла ошибка при выполнении метода 'findAll'");
    }

    @Override
    public List<Matches> findMatchesByPlayerName(String name, int page, int pageSize) {
        return HibernateSessionManager.performSessionQuery(session ->
                        session.createQuery(
                                        "FROM Matches m WHERE m.player1.name = :name OR m.player2.name = :name", Matches.class)
                                .setParameter("name", name)
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .list(),
                "Произошла ошибка при выполнении метода 'findMatchesByPlayerName'"
        );
    }

    @Override
    public long countMatches() {
        return (long) HibernateSessionManager.performSessionQuery(session ->
                session.createQuery(
                        "SELECT COUNT(m) FROM Matches m"
                )
                        .uniqueResult(),
                "Произошла ошибка при выполнении метода 'countMatches'"
        );
    }

    @Override
    public long countMatchesByPlayerName(String playerName) {
        return (long) HibernateSessionManager.performSessionQuery(session ->
                session.createQuery(
                        "SELECT COUNT(m) FROM Matches m WHERE m.player1.name = :name OR m.player2.name = :name")
                        .setParameter("name", playerName)
                        .uniqueResult(),
                "Произошла ошибка при выполнении метода 'countMatchesByPlayerName'"
        );
    }

    @Override
    public Matches findById(int id) {
        return HibernateSessionManager.performSessionQuery(session -> session.get(Matches.class, id),
                "Произошла ошибка при выполнении метода 'findById'");
    }

    @Override
    public void save(Matches matches) {
        HibernateSessionManager.performTransaction(session -> session.save(matches));
    }

    @Override
    public void update(Matches matches) {
        HibernateSessionManager.performTransaction(session -> session.update(matches));
    }

    @Override
    public void delete(Matches matches) {
        HibernateSessionManager.performTransaction(session -> session.delete(matches));
    }
}
