package ru.dragomirov.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.commons.BaseServlet;
import ru.dragomirov.dao.MatchesDAO;
import ru.dragomirov.models.Matches;

import java.io.IOException;

@WebServlet(name = "matchScoreServlet", urlPatterns = "/match-score")
public class matchScoreServlet extends BaseServlet {
    private MatchesDAO matchesDAO;

    @Override
    public void init() {
        matchesDAO = new MatchesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int matchId = Integer.parseInt(req.getParameter("uuid"));
            req.setAttribute("match", matchesDAO.findById(matchId));
            req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
        } catch (Exception e) {
            http500Errors(resp, e, "Ошибка при загрузке страницы.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int matchId = Integer.parseInt(req.getParameter("uuid"));
            Matches match = matchesDAO.findById(matchId);

            if (match == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
                return;
            }

            String action = req.getParameter("action");
            if ("player1_won_point".equals(action)) {
                match.player1WonPoint();
            } else if ("player2_won_point".equals(action)) {
                match.player2WonPoint();
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
            }

            resp.sendRedirect("/match-score?uuid=" + matchId);  // Перенаправление обратно на страницу счёта
        } catch (Exception e) {
            http500Errors(resp, e, "Ошибка при обработке POST-запроса.");
        }
    }
}
