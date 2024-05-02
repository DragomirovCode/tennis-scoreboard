package ru.dragomirov.dao;

import ru.dragomirov.commons.HibernateSessionManager;
import ru.dragomirov.entities.Players;

import java.util.List;
import java.util.Optional;

public class HibernatePlayersDAO implements CrudDAO<Players, Integer> {

    @Override
    public Optional<Players> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Players> findAll() {
        return null;
    }

    @Override
    public void save(Players entity) {
        HibernateSessionManager.performTransaction(session -> session.save(entity));
    }

    @Override
    public Optional<Players> update(Players entity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer integer) {

    }
}
