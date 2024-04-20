package ru.dragomirov.dao;

import ru.dragomirov.models.Players;
import ru.dragomirov.repositories.PlayersRepository;
import ru.dragomirov.commons.HibernateSessionManager;

import java.util.List;

public class PlayersDAO implements PlayersRepository {
    @Override
    public List<Players> findAll() {
        return HibernateSessionManager.performSessionQuery(session -> session.createQuery("FROM Players", Players.class).list(),
                "Произошла ошибка при выполнении метода 'findAll'");
    }

    @Override
    public Players findById(int id) {
        return HibernateSessionManager.performSessionQuery(session -> session.get(Players.class, id),
                "Произошла ошибка при выполнении метода 'findById'");
    }

    @Override
    public Players findByName(String name) {
        return HibernateSessionManager.performSessionQuery(session ->
                        session.createQuery("FROM Players WHERE name = :name", Players.class)
                                .setParameter("name", name)
                                .uniqueResult(),
                "Произошла ошибка при выполнении метода 'findByName'"
        );
    }

    @Override
    public void save(Players player) {
        HibernateSessionManager.performTransaction(session -> session.save(player));
    }

    @Override
    public void update(Players player) {
        HibernateSessionManager.performTransaction(session -> session.update(player));
    }

    @Override
    public void delete(Players player) {
        HibernateSessionManager.performTransaction(session -> session.delete(player));
    }
}
