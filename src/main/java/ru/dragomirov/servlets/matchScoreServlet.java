package ru.dragomirov.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.commons.BaseServlet;
import ru.dragomirov.dao.MatchesDAO;

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
}
