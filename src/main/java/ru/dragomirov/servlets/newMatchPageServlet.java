package ru.dragomirov.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.commons.BaseServlet;
import ru.dragomirov.dao.MatchesDAO;
import ru.dragomirov.dao.PlayersDAO;
import ru.dragomirov.models.Matches;
import ru.dragomirov.models.Players;

import java.io.IOException;

@WebServlet(name = "newMatchPageServlet", urlPatterns = "/new-match")
public class newMatchPageServlet extends BaseServlet {
    private MatchesDAO matchesDAO;
    private PlayersDAO playersDAO;

    @Override
    public void init() {
        matchesDAO = new MatchesDAO();
        playersDAO = new PlayersDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Передаем управление JSP-странице
            req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
        } catch (Exception e) {
            http500Errors(resp, e, "Ошибка при загрузке страницы.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String player1Name = req.getParameter("player1");
            String player2Name = req.getParameter("player2");

            if (player1Name == null || player2Name == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Ошибка: оба имени игроков должны быть указаны.");
                return;
            }

            Players player1 = playersDAO.findByName(player1Name);
            if (player1 == null) {
                player1 = new Players(player1Name);
                playersDAO.save(player1);
            }

            Players player2 = playersDAO.findByName(player2Name);
            if (player2 == null) {
                player2 = new Players(player2Name);
                playersDAO.save(player2);
            }

            Matches match = new Matches(player1, player2, null);
            matchesDAO.save(match);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Новый матч успешно создан.");
        } catch (Exception e) {
            http500Errors(resp, e, "Ошибка при создании матча.");
        }
    }
}
