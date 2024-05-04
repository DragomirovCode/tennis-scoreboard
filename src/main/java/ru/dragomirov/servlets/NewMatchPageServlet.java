    package ru.dragomirov.servlets;
    
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import ru.dragomirov.dto.MatchDTOFactory;
    import ru.dragomirov.dto.MatchDTO;
    import ru.dragomirov.dto.PlayerDTO;
    import ru.dragomirov.dto.PlayerDTOFactory;
    
    import java.io.IOException;

    /**
     * NewMatchPageServlet используется для обрабатываение запросов,
     * связанные с созданием нового матча.
     */
    @WebServlet(name = "NewMatchPageServlet", urlPatterns = "/new-match")
    public class NewMatchPageServlet extends HttpServlet {
        private MatchDTOFactory matchDTOFactory;
        private PlayerDTOFactory playerDTOFactory;
    
        @Override
        public void init() {
            this.matchDTOFactory = new MatchDTOFactory();
            this.playerDTOFactory = new PlayerDTOFactory();
        }
    
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            try {
                req.getRequestDispatcher("/new-match.jsp").forward(req, resp);
            } catch (Exception e) {
                req.setAttribute("errorMessage", "Ошибка при загрузке страницы.");
                req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
            }
        }
    
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
            try {
                String player1Name = req.getParameter("player1");
                String player2Name = req.getParameter("player2");
    
                if (player1Name == null || player2Name == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Ошибка: оба имени игроков должны быть указаны.");
                    return;
                }
    
                PlayerDTO player1DTO = playerDTOFactory.createPlayers(player1Name, "0");
    
                PlayerDTO player2DTO = playerDTOFactory.createPlayers(player2Name, "0");
    
                MatchDTO match = matchDTOFactory.createMatches(player1DTO, player2DTO);

                req.getSession().setAttribute("match", match);

                resp.sendRedirect("/match-score?uuid=" + match.getId());
            } catch (Exception e) {
                req.setAttribute("errorMessage", "Ошибка при добавлении нового матча.");
                req.getRequestDispatcher("/errors/serverError.jsp").forward(req, resp);
            }
        }
    }
