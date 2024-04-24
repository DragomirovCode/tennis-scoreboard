<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="match" scope="request" type="ru.dragomirov.models.Matches" />
<html>
<head>
    <title>Счет Матча</title>
</head>
<body>
<h2>Счет Матча</h2>
<!-- Таблица, показывающая счет для каждого игрока -->
<table border="1">
    <tr>
        <th>Игрок</th>
        <th>Счет</th>
    </tr>
    <tr>
        <td>${match.player1.name}</td>
        <td>${match.score.getPoints(match.player1)}</td>
        <td>
            <form method="post" action="/match-score?uuid=${match.id}">
                <input type="hidden" name="action" value="player1_won_point">
                <button type="submit">Игрок 1 выиграл очко</button>
            </form>
        </td>
    </tr>
    <tr>
        <td>${match.player2.name}</td>
        <td>${match.score.getPoints(match.player1)}</td>
        <td>
            <form method="post" action="/match-score?uuid=${match.id}">
                <input type="hidden" name="action" value="player2_won_point">
                <button type="submit">Игрок 2 выиграл очко</button>
            </form>
        </td>
    </tr>
</table>

</body>
</html>
