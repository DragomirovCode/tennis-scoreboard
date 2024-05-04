package ru.dragomirov.services;

import ru.dragomirov.dao.HibernateMatchesDAO;
import ru.dragomirov.entities.Matches;

import java.util.List;

/**
 * MatchesDisplayService реализует бизнес логику MatchesDisplayServlet
 */
public class MatchesDisplayService {
    private final HibernateMatchesDAO hibernateMatchesDAO;
    private static final int PAGE_SIZE = 5;

    public MatchesDisplayService() {
        this.hibernateMatchesDAO = new HibernateMatchesDAO();
    }

    public List<Matches> getMatches(String playerName, int page) {
        if (playerName != null && !playerName.isEmpty()) {
            return hibernateMatchesDAO.findMatchesByPlayerName(playerName, page, PAGE_SIZE);
        } else {
            return hibernateMatchesDAO.findAll(page, PAGE_SIZE);
        }
    }

    public long getTotalMatches(String playerName) {
        if (playerName != null && !playerName.isEmpty()) {
            return hibernateMatchesDAO.countMatchesByPlayerName(playerName);
        } else {
            return hibernateMatchesDAO.countMatches();
        }
    }

    public boolean hasNextPage(int page, long totalMatches) {
        return (long) page * PAGE_SIZE < totalMatches;
    }
}
