package ru.dragomirov.dao;

import ru.dragomirov.entities.Matches;

import java.util.List;

public interface MatchesDAO extends CrudDAO<Matches, Integer> {
    List<Matches> findAll(int page, int pageSize);
    List<Matches> findMatchesByPlayerName(String name, int page, int pageSize);
    long countMatches();
    long countMatchesByPlayerName(String playerName);
}
