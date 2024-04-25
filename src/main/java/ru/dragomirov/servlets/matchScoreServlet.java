package ru.dragomirov.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.commons.BaseServlet;
import ru.dragomirov.dto.MatchesDTO;
import ru.dragomirov.services.MatchesService;

import java.io.IOException;

@WebServlet(name = "matchScoreServlet", urlPatterns = "/match-score")
public class matchScoreServlet extends BaseServlet {
    private MatchesService matchesService;
    @Override
    public void init() {
        matchesService = new MatchesService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Получаем идентификатор матча из строки запроса
            String uuidStr = req.getParameter("uuid");

            if (uuidStr == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Ошибка: параметр uuid не был передан.");
                return;
            }

            // Преобразуем строку в целое число
            int matchId;
            try {
                matchId = Integer.parseInt(uuidStr);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Ошибка: неверный формат идентификатора uuid.");
                return;
            }

            // Используем matchId для поиска соответствующего матча
            MatchesDTO match = (MatchesDTO) req.getSession().getAttribute("match");

            if (match == null || match.getId() != matchId) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Ошибка: матч с таким идентификатором не найден.");
                return;
            }

            // Передаем объект в JSP-страницу
            req.setAttribute("match", match);
            req.getRequestDispatcher("/match-score.jsp").forward(req, resp);

        } catch (Exception e) {
            http500Errors(resp, e, "Ошибка при загрузке страницы.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Получаем идентификатор матча
            String uuidStr = req.getParameter("uuid");
            if (uuidStr == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Ошибка: параметр uuid отсутствует.");
                return;
            }

            // Извлекаем идентификатор и действие
            int matchId = Integer.parseInt(uuidStr);
            String action = req.getParameter("action");

            if (action == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Ошибка: параметр 'action' отсутствует.");
                return;
            }

            // Получаем объект матча из сессии
            MatchesDTO match = (MatchesDTO) req.getSession().getAttribute("match");

            if (match == null || match.getId() != matchId) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Ошибка: матч не найден.");
                return;
            }

            // Обработка действия
            switch (action) {
                case "player1_won_point":
                    matchesService.addPointsToPlayers(match.getPlayer1(), match.getPlayer2());
                    break;
                case "player2_won_point":
                    matchesService.addPointsToPlayers(match.getPlayer2(), match.getPlayer1());
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Ошибка: неизвестное действие.");
                    return;
            }

            // Сохраняем изменения в сессии и отправляем успешный ответ
            req.getSession().setAttribute("match", match);

            // Редирект с использованием идентификатора матча
            resp.sendRedirect("/match-score?uuid=" + match.getId());
        } catch (Exception e) {
            // В случае ошибки
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Ошибка при обработке запроса.");
        }
    }
}
