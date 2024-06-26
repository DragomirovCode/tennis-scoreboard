package ru.dragomirov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.dto.MatchDTO;
import ru.dragomirov.services.MatchScoreCalculationService;

import java.io.IOException;

/**
 * MatchScoreServlet используется для обрабатывание запросов, связанные с очками матча,
 * включая получение и обновление данных о результатах.
 */
@WebServlet(name = "MatchScoreServlet", urlPatterns = "/match-score")
public class MatchScoreServlet extends HttpServlet {
    private MatchScoreCalculationService matchesService;
    @Override
    public void init() {
        this.matchesService = new MatchScoreCalculationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
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
            MatchDTO match = (MatchDTO) req.getSession().getAttribute("match");

            if (match == null || match.getId() != matchId) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Ошибка: матч с таким идентификатором не найден.");
                return;
            }

            req.setAttribute("match", match);
            req.getRequestDispatcher("/match-score.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке страницы.");
            req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
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
            MatchDTO match = (MatchDTO) req.getSession().getAttribute("match");

            if (match == null || match.getId() != matchId) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Ошибка: матч не найден.");
                return;
            }

            // Обработка действия
            switch (action) {
                case "player1_won_point":
                    matchesService.addPointsToPlayers(match, match.getPlayer1(), match.getPlayer2());
                    break;
                case "player2_won_point":
                    matchesService.addPointsToPlayers(match, match.getPlayer2(), match.getPlayer1());
                    break;
                default:
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Ошибка: неизвестное действие.");
                    return;
            }

            // Сохраняем изменения в сессии и отправляем успешный ответ
            req.getSession().setAttribute("match", match);

            resp.sendRedirect("/match-score?uuid=" + match.getId());
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при добавлении очков.");
            req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
        }
    }
}
