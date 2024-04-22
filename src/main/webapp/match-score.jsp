<jsp:useBean id="match" scope="request" type="ru.dragomirov.models.Matches"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Счёт</h2>
<table>
    <tr>
        <th>Игрок</th>
        <th>Счёт</th>
    </tr>
    <tr>
        <td>${match.player1.name}</td>
        <td>${match.score.getScore(match.player1)}</td>
        <td>
            <form method="post" action="/match-score?uuid=${param.uuid}">
                <input type="hidden" name="action" value="player1_won_point">
                <button type="submit">Игрок 1 выиграл очко</button>
            </form>
        </td>
    </tr>
    <tr>
        <td>${match.player2.name}</td>
        <td>${match.score.getScore(match.player2)}</td>
        <td>
            <form method="post" action="/match-score?uuid=${param.uuid}">
                <input type="hidden" name="action" value="player2_won_point">
                <button type="submit">Игрок 2 выиграл очко</button>
            </form>
        </td>
    </tr>
</table>
</body>
</html>
