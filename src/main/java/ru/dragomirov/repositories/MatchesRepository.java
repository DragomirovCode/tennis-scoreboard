package ru.dragomirov.repositories;

import ru.dragomirov.models.Matches;

import java.util.List;

public interface MatchesRepository {
    List<Matches> findAll();
    Matches findById(int id);
    void save(Matches matches);
    void update(Matches matches);
    void delete(Matches matches);
}
