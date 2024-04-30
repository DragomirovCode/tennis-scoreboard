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
    private static final int PAGE_SIZE = 1;

    @Override
    public void init() {
        matchesDAO = new MatchesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Получаем имя игрока из параметров запроса
            String playerName = req.getParameter("name");

            // Определяем номер текущей страницы (если не задан, то 1)
            int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;

            // Список матчей, который будет возвращен в ответе
            List<Matches> matches;

            // Запрашиваем на одну запись больше, чем PAGE_SIZE
            if (playerName != null && !playerName.isEmpty()) {
                matches = matchesDAO.findMatchesByPlayerName(playerName, page, PAGE_SIZE);
            } else {
                matches = matchesDAO.findAll(page, PAGE_SIZE);
            }

            // Проверяем, есть ли данные для следующей страницы
            long totalMatches = matchesDAO.countMatches(); // Новый метод, который возвращает общее количество матчей
            boolean hasNextPage = page * PAGE_SIZE < totalMatches;

            // Добавляем атрибуты для запроса
            req.setAttribute("matches", matches); // Список матчей для отображения
            req.setAttribute("currentPage", page); // Текущая страница
            req.setAttribute("hasNextPage", hasNextPage); // Есть ли следующая страница

            // Перенаправляем на JSP для отображения
            req.getRequestDispatcher("/matches.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
