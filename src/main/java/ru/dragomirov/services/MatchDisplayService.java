package ru.dragomirov.services;

import ru.dragomirov.dao.HibernateMatchDAO;
import ru.dragomirov.entities.Match;

import java.util.List;

/**
 * MatchDisplayService реализует бизнес логику MatchDisplayServlet
 */
public class MatchDisplayService {
    private final HibernateMatchDAO hibernateMatchDAO;
    private static final int PAGE_SIZE = 5;

    public MatchDisplayService() {
        this.hibernateMatchDAO = new HibernateMatchDAO();
    }

    public List<Match> getMatches(String playerName, int page) {
        if (playerName != null && !playerName.isEmpty()) {
            return hibernateMatchDAO.findMatchesByPlayerName(playerName, page, PAGE_SIZE);
        } else {
            return hibernateMatchDAO.findAll(page, PAGE_SIZE);
        }
    }

    public long getTotalMatches(String playerName) {
        if (playerName != null && !playerName.isEmpty()) {
            return hibernateMatchDAO.countMatchesByPlayerName(playerName);
        } else {
            return hibernateMatchDAO.countMatches();
        }
    }

    public boolean hasNextPage(int page, long totalMatches) {
        return (long) page * PAGE_SIZE < totalMatches;
    }
}
