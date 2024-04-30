package ru.dragomirov.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.dao.MatchesDAO;
import ru.dragomirov.entities.Matches;

import java.util.List;

@WebServlet(name = "MatchHistoryServlet", urlPatterns = "/matches")
public class MatchHistoryServlet extends HttpServlet {
    private MatchesDAO matchesDAO = new MatchesDAO();
    private static final int PAGE_SIZE = 10; // количество матчей на странице

    @Override
    public void init() {
        matchesDAO = new MatchesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String playerName = req.getParameter("name");
            List<Matches> matches;
            if (playerName != null && !playerName.isEmpty()) {
                matches = matchesDAO.findMatchesByPlayerName(playerName);
            } else {
                matches = matchesDAO.findAll();
            }
            req.setAttribute("matches", matches);
            req.getRequestDispatcher("/matches.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
