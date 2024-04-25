package ru.dragomirov.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.commons.BaseServlet;
import ru.dragomirov.dto.MatchesDTOFactory;
import ru.dragomirov.dto.MatchesDTO;
import ru.dragomirov.dto.PlayersDTO;
import java.io.IOException;

@WebServlet(name = "newMatchPageServlet", urlPatterns = "/new-match")
public class newMatchPageServlet extends BaseServlet {
    private MatchesDTOFactory factory;

    @Override
    public void init() {
        factory = new MatchesDTOFactory();
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

            PlayersDTO player1DTO = new PlayersDTO();
            player1DTO.setName(player1Name);

            PlayersDTO player2DTO = new PlayersDTO();
            player2DTO.setName(player2Name);

            MatchesDTO match = factory.createMatches(player1DTO, player2DTO, "0-0");

            // Сохранение в сессии
            req.getSession().setAttribute("match", match);

            // Редирект с использованием идентификатора матча
            resp.sendRedirect("/match-score?uuid=" + match.getId());
        } catch (Exception e) {
            http500Errors(resp, e, "Ошибка при создании матча.");
        }
    }
}
