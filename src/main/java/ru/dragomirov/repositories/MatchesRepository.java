package ru.dragomirov.repositories;

import ru.dragomirov.entities.Matches;

import java.util.List;

public interface MatchesRepository {
    List<Matches> findAll(int page, int pageSize);
    List<Matches> findMatchesByPlayerName(String name, int page, int pageSize);
    long countMatches();
    long countMatchesByPlayerName(String playerName);
    Matches findById(int id);
    void save(Matches matches);
    void update(Matches matches);
    void delete(Matches matches);
}
