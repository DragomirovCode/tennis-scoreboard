package ru.dragomirov.dao;

import ru.dragomirov.commons.HibernateSessionManager;
import ru.dragomirov.entities.Matches;

import java.util.List;
import java.util.Optional;

public class HibernateMatchesDAO implements MatchesDAO {
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
    public Optional<Matches> findById(Integer id) {
        return Optional.ofNullable(HibernateSessionManager.performSessionQuery(session -> session.get(Matches.class, id),
                "Произошла ошибка при выполнении метода 'findById'"));
    }

    @Override
    public List<Matches> findAll() {
        return null;
    }

    @Override
    public void save(Matches entity) {
        HibernateSessionManager.performTransaction(session -> session.save(entity));
    }

    @Override
    public Optional<Matches> update(Matches entity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer integer) {
        HibernateSessionManager.performTransaction(session -> session.delete(integer));
    }
}
