<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="match" scope="request" type="ru.dragomirov.dto.MatchesDTO" />
<html>
<head>
    <title>Счет Матча</title>
</head>
<body>
<h2>Счет Матча</h2>

<!-- Таблица, показывающая счет и выигранные геймы для каждого игрока -->
<table border="1">
    <tr>
        <th>Игрок</th>
        <th>Счёт</th>
        <th>Выигранные геймы</th> <!-- Добавление колонки с выигранными геймами -->
        <th>Действие</th>
    </tr>
    <tr>
        <td>${match.player1.name}</td>
        <td>${match.player1.score}</td> <!-- Отображение счёта -->
        <td>${match.player1.gamesWon}</td> <!-- Отображение количества выигранных геймов -->
        <td>
            <form method="post" action="/match-score?uuid=${match.id}">
                <input type="hidden" name="action" value="player1_won_point">
                <button type="submit">Игрок 1 выиграл очко</button>
            </form>
        </td>
    </tr>
    <tr>
        <td>${match.player2.name}</td>
        <td>${match.player2.score}</td> <!-- Отображение счёта -->
        <td>${match.player2.gamesWon}</td> <!-- Отображение количества выигранных геймов -->
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
