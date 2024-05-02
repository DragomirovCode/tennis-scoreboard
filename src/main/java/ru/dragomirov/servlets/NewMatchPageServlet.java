package ru.dragomirov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.dto.MatchesDTOFactory;
import ru.dragomirov.dto.MatchesDTO;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.dto.PlayersDTOFactory;

import java.io.IOException;

@WebServlet(name = "NewMatchPageServlet", urlPatterns = "/new-match")
public class NewMatchPageServlet extends HttpServlet {
    private MatchesDTOFactory matchesDTOFactory;
    private PlayersDTOFactory playersDTOFactory;

    @Override
    public void init() {
        matchesDTOFactory = new MatchesDTOFactory();
        playersDTOFactory = new PlayersDTOFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            // Передаем управление JSP-странице
            req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке страницы.");
            req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String player1Name = req.getParameter("player1");
            String player2Name = req.getParameter("player2");

            if (player1Name == null || player2Name == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Ошибка: оба имени игроков должны быть указаны.");
                return;
            }

            PlayersDTO player1DTO = playersDTOFactory.createPlayers(player1Name, "0");

            PlayersDTO player2DTO = playersDTOFactory.createPlayers(player2Name, "0");

            MatchesDTO match = matchesDTOFactory.createMatches(player1DTO, player2DTO);

            // Сохранение в сессии
            req.getSession().setAttribute("match", match);

            // Редирект с использованием идентификатора матча
            resp.sendRedirect("/match-score?uuid=" + match.getId());
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при добавлении нового матча.");
            req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
        }
    }
}
