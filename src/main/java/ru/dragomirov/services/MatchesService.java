package ru.dragomirov.services;

import ru.dragomirov.dto.PlayersDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

//TODO: Нужно добавить условие, что игра идёт до 3-х сетов

//TODO: Нужно добавить, добавление сущности в бд, после 3-х сетов.

public class MatchesService {
    private final Map<String, BiConsumer<PlayersDTO, PlayersDTO>> scoreHandlers = new HashMap<>();

    {
        scoreHandlers.put("40:30", this::winGameAndReset);
        scoreHandlers.put("40:15", this::winGameAndReset);
        scoreHandlers.put("40:0",  this::winGameAndReset);
        scoreHandlers.put("40:40", this::handleDeuce);
        scoreHandlers.put("40:AD", this::handleDeuce);
        scoreHandlers.put("AD:40", this::winGameAndReset);
        scoreHandlers.put("AD:AD", this::winGameAndReset);
    }

    public void addPointsToPlayers(PlayersDTO player, PlayersDTO opponent) {
        if (player.getGamesWon() == 6 && opponent.getGamesWon() == 6) {
            handleTieBreak(player, opponent);
            return;
        }

        if (player.getSet() == 3 || opponent.getSet() == 3) {
            resetGamesAfterWinning(player, opponent);
            resetScoreAfterWinning(player, opponent);
            resetSetAfterWinning(player, opponent);
        }

        String scoreKey = player.getScore() + ":" + opponent.getScore();
        if (scoreHandlers.containsKey(scoreKey)) {
            scoreHandlers.get(scoreKey).accept(player, opponent);
        } else {
            addPoint(player);
        }
    }

    private void winGameAndReset(PlayersDTO player, PlayersDTO opponent) {
        winGame(player, opponent);
        resetScoreAfterWinning(player, opponent);
    }

    private void winGame(PlayersDTO player, PlayersDTO opponent) {
        player.setGamesWon(getNextGameCount(player.getGamesWon()));
        if (player.getGamesWon() >= 6 && (player.getGamesWon() - opponent.getGamesWon() >= 2)) {
            player.setSet(player.getSet() + 1);
            resetGamesAfterWinning(player, opponent);
        }
    }

    private void handleDeuce(PlayersDTO player, PlayersDTO opponent) {
        if (player.getScore().equals("AD")) {
            winGame(player, opponent);
            resetScoreAfterWinning(player, opponent);
        } else if (opponent.getScore().equals("AD")) {
            opponent.setScore("40");
            player.setScore("AD");
        } else {
            player.setScore("AD");
        }
    }

    private void handleTieBreak(PlayersDTO player, PlayersDTO opponent) {
        player.setScore(String.valueOf(Integer.parseInt(player.getScore()) + 1));
        if (Integer.parseInt(player.getScore()) >= 7 &&
                (Integer.parseInt(player.getScore()) - Integer.parseInt(opponent.getScore()) >= 2)) {
            player.setSet(player.getSet() + 1);
            resetGamesAfterWinning(player, opponent);
            resetScoreAfterWinning(player, opponent);
        }
    }

    private void addPoint(PlayersDTO player) {
        String additionalPoints = getNextPoint(player.getScore());
        player.setScore(additionalPoints);
    }

    private String getNextPoint(String currentPoints) {
        switch (currentPoints) {
            case "0":
                return "15";
            case "15":
                return "30";
            case "30":
                return "40";
        }
        return currentPoints;
    }

    private int getNextGameCount(int countScore) {
        return countScore + 1;
    }

    private void resetScoreAfterWinning(PlayersDTO player, PlayersDTO opponent) {
        player.setScore("0");
        opponent.setScore("0");
    }

    private void resetGamesAfterWinning(PlayersDTO player, PlayersDTO opponent) {
        player.setGamesWon(0);
        opponent.setGamesWon(0);
    }

    private void resetSetAfterWinning(PlayersDTO player, PlayersDTO opponent) {
        player.setSet(0);
        opponent.setSet(0);
    }
}
