package ru.dragomirov.dao;

import ru.dragomirov.commons.HibernateSessionManager;
import ru.dragomirov.models.Matches;
import ru.dragomirov.repositories.MatchesRepository;

import java.util.List;

public class MatchesDAO implements MatchesRepository {
    @Override
    public List<Matches> findAll() {
        return HibernateSessionManager.performSessionQuery(session -> session.createQuery("FROM Matches", Matches.class).list(),
                "Произошла ошибка при выполнении метода 'findAll'");
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
