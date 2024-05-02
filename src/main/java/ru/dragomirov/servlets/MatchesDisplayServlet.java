package ru.dragomirov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.dao.HibernateMatchesDAO;
import ru.dragomirov.entities.Matches;

import java.io.IOException;
import java.util.List;

/**
 * MatchesDisplayServlet используется для отображения списка матчей,
 * включая их идентификатор, имена игроков и победителя.
 */
@WebServlet(name = "MatchesDisplayServlet", urlPatterns = "/matches")
public class MatchesDisplayServlet extends HttpServlet {
    private HibernateMatchesDAO hibernateMatchesDAO = new HibernateMatchesDAO();
    private static final int PAGE_SIZE = 1;

    @Override
    public void init() {
        this.hibernateMatchesDAO = new HibernateMatchesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String playerName = req.getParameter("filter_by_player_name");

            int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;

            List<Matches> matches;

            long totalMatches;
            if (playerName != null && !playerName.isEmpty()) {
                matches = hibernateMatchesDAO.findMatchesByPlayerName(playerName, page, PAGE_SIZE);
                totalMatches = hibernateMatchesDAO.countMatchesByPlayerName(playerName);
            } else {
                matches = hibernateMatchesDAO.findAll(page, PAGE_SIZE);
                totalMatches = hibernateMatchesDAO.countMatches();
            }

            // Проверяем, есть ли данные для следующей страницы
            boolean hasNextPage = page * PAGE_SIZE < totalMatches;

            req.setAttribute("matches", matches);
            req.setAttribute("currentPage", page);
            req.setAttribute("hasNextPage", hasNextPage);
            req.setAttribute("playerName", playerName);

            req.getRequestDispatcher("/matches.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке страницы.");
            req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
        }
    }
}
