package ru.dragomirov.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "MainPageServlet", urlPatterns = "/")
public class MainPageServlet extends HttpServlet {
    @Override
    public void init() {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            // Передаем управление JSP-странице
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        } catch (Exception e) {
            // Перенаправляем на страницу ошибки
            req.setAttribute("errorMessage", "Ошибка при загрузке страницы.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
