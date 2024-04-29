package ru.dragomirov.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dragomirov.commons.BaseServlet;

import java.io.IOException;

@WebServlet(name = "MainPageServlet", urlPatterns = "/")
public class MainPageServlet extends BaseServlet {
    @Override
    public void init() {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Передаем управление JSP-странице
            req.getRequestDispatcher("/main.jsp").forward(req, resp);
        } catch (Exception e) {
            http500Errors(resp, e, "Ошибка при загрузке страницы.");
        }
    }
}
