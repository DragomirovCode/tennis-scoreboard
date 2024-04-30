<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, ru.dragomirov.entities.Matches" %>

<html>
<head>
    <title>Список матчей</title>
    <style>
        .inline-buttons {
            display: inline-block;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<h1>Список матчей</h1>

<div>
    <form action="/matches" method="get" class="inline-buttons">
        <input type="text" name="filter_by_player_name" placeholder="Имя игрока">
        <input type="submit" value="Поиск">
    </form>

    <form action="/matches" method="get" class="inline-buttons">
        <input type="hidden" name="filter_by_player_name">
        <input type="submit" value="Сбросить">
    </form>
</div>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Игрок 1</th>
        <th>Игрок 2</th>
        <th>Победитель</th>
    </tr>

    <%
        List<Matches> matches = (List<Matches>) request.getAttribute("matches");
        int currentPage = (int) request.getAttribute("currentPage");
        boolean hasNextPage = (boolean) request.getAttribute("hasNextPage");
        String playerName = (String) request.getAttribute("playerName");
        if (matches != null && !matches.isEmpty()) {
            for (Matches match : matches) {
    %>
    <tr>
        <td><%= match.getId() %></td>
        <td><%= match.getPlayer1().getName() %></td>
        <td><%= match.getPlayer2().getName() %></td>
        <td><%= (match.getWinner() != null) ? match.getWinner().getName() : "Нет данных" %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4">Нет данных о матчах</td>
    </tr>
    <%
        }
    %>
</table>

<div>
    <% if (currentPage > 1) { %>
    <a href="/matches?page=<%= currentPage - 1 %><%= playerName != null ? "&filter_by_player_name=" + playerName : "" %>">Предыдущая страница</a>
    <% } %>
    <% if (hasNextPage) { %>
    <a href="/matches?page=<%= currentPage + 1 %><%= playerName != null ? "&filter_by_player_name=" + playerName : "" %>">Следующая страница</a>
    <% } %>
</div>

<br>

<a href="${pageContext.request.contextPath}/">Перейти на главную страницу</a>

</body>
</html>
