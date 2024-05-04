package ru.dragomirov.dao;

import ru.dragomirov.entities.Match;
import ru.dragomirov.utils.HibernateSessionManagerUtil;

import java.util.List;
import java.util.Optional;

public class HibernateMatchDAO implements MatchDAO {
    @Override
    public List<Match> findAll(int page, int pageSize) {
        return HibernateSessionManagerUtil.performSessionQuery(session -> session.createQuery("FROM Match", Match.class)
                        .setFirstResult((page - 1) * pageSize)
                        .setMaxResults(pageSize)
                        .list(),
                "Произошла ошибка при выполнении метода 'findAll'");
    }

    @Override
    public List<Match> findMatchesByPlayerName(String name, int page, int pageSize) {
        return HibernateSessionManagerUtil.performSessionQuery(session ->
                        session.createQuery(
                                        "FROM Match m WHERE m.player1.name = :name OR m.player2.name = :name", Match.class)
                                .setParameter("name", name)
                                .setFirstResult((page - 1) * pageSize)
                                .setMaxResults(pageSize)
                                .list(),
                "Произошла ошибка при выполнении метода 'findMatchesByPlayerName'"
        );
    }

    @Override
    public long countMatches() {
        return (long) HibernateSessionManagerUtil.performSessionQuery(session ->
                session.createQuery(
                        "SELECT COUNT(m) FROM Match m"
                )
                        .uniqueResult(),
                "Произошла ошибка при выполнении метода 'countMatches'"
        );
    }

    @Override
    public long countMatchesByPlayerName(String playerName) {
        return (long) HibernateSessionManagerUtil.performSessionQuery(session ->
                session.createQuery(
                        "SELECT COUNT(m) FROM Match m WHERE m.player1.name = :name OR m.player2.name = :name")
                        .setParameter("name", playerName)
                        .uniqueResult(),
                "Произошла ошибка при выполнении метода 'countMatchesByPlayerName'"
        );
    }

    @Override
    public Optional<Match> findById(Integer id) {
        return Optional.ofNullable(HibernateSessionManagerUtil.performSessionQuery(session -> session.get(Match.class, id),
                "Произошла ошибка при выполнении метода 'findById'"));
    }

    @Override
    public List<Match> findAll() {
        return null;
    }

    @Override
    public void save(Match entity) {
        HibernateSessionManagerUtil.performTransaction(session -> session.save(entity));
    }

    @Override
    public Optional<Match> update(Match entity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer integer) {
        HibernateSessionManagerUtil.performTransaction(session -> session.delete(integer));
    }
}
