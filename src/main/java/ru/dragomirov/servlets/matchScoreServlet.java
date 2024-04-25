package ru.dragomirov.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.commons.BaseServlet;
import ru.dragomirov.dto.MatchesDTO;

import java.io.IOException;

@WebServlet(name = "matchScoreServlet", urlPatterns = "/match-score")
public class matchScoreServlet extends BaseServlet {
    @Override
    public void init() {}

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
}
