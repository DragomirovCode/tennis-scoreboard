package ru.dragomirov.dao;

import ru.dragomirov.entities.Player;
import ru.dragomirov.utils.HibernateSessionManagerUtil;

import java.util.List;
import java.util.Optional;

public class HibernatePlayerDAO implements CrudDAO<Player, Integer> {

    @Override
    public Optional<Player> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Player> findAll() {
        return null;
    }

    @Override
    public void save(Player entity) {
        HibernateSessionManagerUtil.performTransaction(session -> session.save(entity));
    }

    @Override
    public Optional<Player> update(Player entity) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer integer) {

    }
}
