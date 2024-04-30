<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, ru.dragomirov.entities.Matches" %>

<html>
<head>
    <title>Список матчей</title>
</head>
<body>
<h1>Список матчей</h1>

<form action="/matches" method="get">
    <input type="text" name="name" placeholder="Имя игрока">
    <input type="submit" value="Искать">
</form>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Игрок 1</th>
        <th>Игрок 2</th>
        <th>Победитель</th>
    </tr>

    <%
        List<Matches> matches = (List<Matches>) request.getAttribute("matches");
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
</body>
</html>
