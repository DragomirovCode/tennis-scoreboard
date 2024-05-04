package ru.dragomirov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.entities.Match;
import ru.dragomirov.services.MatchDisplayService;

import java.io.IOException;
import java.util.List;

/**
 * MatchDisplayServlet используется для отображения списка матчей,
 * включая их идентификатор, имена игроков и победителя.
 */
@WebServlet(name = "MatchDisplayServlet", urlPatterns = "/matches")
public class MatchDisplayServlet extends HttpServlet {
    private MatchDisplayService matchDisplayService;

    @Override
    public void init() {
        this.matchDisplayService = new MatchDisplayService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String playerName = req.getParameter("filter_by_player_name");

            int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;

            List<Match> matches = matchDisplayService.getMatches(playerName, page);

            long totalMatches = matchDisplayService.getTotalMatches(playerName);

            boolean hasNextPage = matchDisplayService.hasNextPage(page, totalMatches);

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
