package ru.dragomirov.utils;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.h2.tools.Server;

/**
 * H2ServerInitializerUtil используется для инициализации и остановки сервера H2 в контексте веб-приложения.
 */
public class H2ServerInitializerUtil implements ServletContextListener {
    private Server webServer;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8083").start();

            System.out.println("H2 сервер и веб-консоль запущены.");
        } catch (Exception e) {
            System.err.println("Ошибка при запуске сервера H2: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (webServer != null) {
            webServer.stop();
        }
        System.out.println("H2 сервер и веб-консоль остановлены.");
    }
}
