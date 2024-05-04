package ru.dragomirov.dao;

import ru.dragomirov.entities.Match;

import java.util.List;

public interface MatchDAO extends CrudDAO<Match, Integer> {
    List<Match> findAll(int page, int pageSize);
    List<Match> findMatchesByPlayerName(String name, int page, int pageSize);
    long countMatches();
    long countMatchesByPlayerName(String playerName);
}
