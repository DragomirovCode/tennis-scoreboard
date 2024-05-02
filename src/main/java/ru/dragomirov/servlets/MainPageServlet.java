package ru.dragomirov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * MainPageServlet используется в качестве начальной точки
 * входа в веб-приложение, где пользователь может получить доступ
 * к различным разделам сайта или начать навигацию.
 */
@WebServlet(name = "MainPageServlet", urlPatterns = "/")
public class MainPageServlet extends HttpServlet {
    @Override
    public void init() {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке страницы.");
            req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
        }
    }
}
