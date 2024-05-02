package ru.dragomirov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.dao.HibernateMatchesDAO;
import ru.dragomirov.entities.Matches;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MatchesDisplayServlet", urlPatterns = "/matches")
public class MatchesDisplayServlet extends HttpServlet {
    private HibernateMatchesDAO hibernateMatchesDAO = new HibernateMatchesDAO();
    private static final int PAGE_SIZE = 1;

    @Override
    public void init() {
        hibernateMatchesDAO = new HibernateMatchesDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Получаем имя игрока из параметров запроса
            String playerName = req.getParameter("filter_by_player_name");

            // Определяем номер текущей страницы (если не задан, то 1)
            int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;

            // Список матчей, который будет возвращен в ответе
            List<Matches> matches;

            // Запрашиваем на одну запись больше, чем PAGE_SIZE
            long totalMatches;
            if (playerName != null && !playerName.isEmpty()) {
                matches = hibernateMatchesDAO.findMatchesByPlayerName(playerName, page, PAGE_SIZE);
                totalMatches = hibernateMatchesDAO.countMatchesByPlayerName(playerName);
            } else {
                matches = hibernateMatchesDAO.findAll(page, PAGE_SIZE);
                totalMatches = hibernateMatchesDAO.countMatches();
            }

            // Проверяем, есть ли данные для следующей страницы
            boolean hasNextPage = page * PAGE_SIZE < totalMatches;

            // Добавляем атрибуты для запроса
            req.setAttribute("matches", matches); // Список матчей для отображения
            req.setAttribute("currentPage", page); // Текущая страница
            req.setAttribute("hasNextPage", hasNextPage); // Есть ли следующая страница
            req.setAttribute("playerName", playerName); // Имя игрока

            // Перенаправляем на JSP для отображения
            req.getRequestDispatcher("/matches.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке страницы.");
            req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
        }
    }
}
