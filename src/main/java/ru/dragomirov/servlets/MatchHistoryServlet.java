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

    @Override
    public void init() {
        matchesDAO = new MatchesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Matches> matches = matchesDAO.findAll();
            req.setAttribute("matches", matches);
            req.getRequestDispatcher("/matches.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
