package ru.dragomirov.services;

import ru.dragomirov.dao.MatchesDAO;
import ru.dragomirov.dao.PlayersDAO;

public class PlayersService {
    private final MatchesDAO matchesDAO;
    private final PlayersDAO playersDAO;

    public PlayersService(MatchesDAO matchesDAO, PlayersDAO playersDAO) {
        this.matchesDAO = matchesDAO;
        this.playersDAO = playersDAO;
    }
}
