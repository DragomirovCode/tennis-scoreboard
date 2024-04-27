package ru.dragomirov.services;

import ru.dragomirov.dto.PlayersDTO;

public class MatchesService {
    public void addPointsToPlayers(PlayersDTO player, PlayersDTO opponent) {
        if (player.getGamesWon() == 6 && opponent.getGamesWon() == 6) {
            handleTieBreak(player, opponent);
            return;
        }

        if (player.getScore().equals("40") && opponent.getScore().equals("30") ||
                player.getScore().equals("40") && opponent.getScore().equals("15") ||
                player.getScore().equals("40") && opponent.getScore().equals("0")) {
            winGame(player, opponent);
            resetAfterWinning(player);
            resetAfterWinning(opponent);
            return;
        }

        if (isDeuce(player, opponent)) {
            handleDeuce(player, opponent);
            return;
        } else if (player.getScore().equals("40") && opponent.getScore().equals("AD")) {
                handleDeuce(player, opponent);
                return;
        }

        if (player.getScore().equals("AD")) {
            winGame(player, opponent);
            resetAfterWinning(player);
            resetAfterWinning(opponent);
        } else {
            addPoint(player);
        }
    }

    private void winGame(PlayersDTO player, PlayersDTO opponent) {
        player.setGamesWon(getNextGameCount(player.getGamesWon()));
        if (player.getGamesWon() >= 6 && (player.getGamesWon() - opponent.getGamesWon() >= 2)) {
            player.setSet(player.getSet() + 1);
            player.setGamesWon(0);
            opponent.setGamesWon(0);
        }
    }

    private void handleDeuce(PlayersDTO player, PlayersDTO opponent) {
        if (player.getScore().equals("AD")) {
            winGame(player, opponent);
            resetAfterWinning(player);
            resetAfterWinning(opponent);
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
            player.setGamesWon(0);
            player.setScore("0");
            opponent.setGamesWon(0);
            opponent.setScore("0");
        }
    }

    private void addPoint(PlayersDTO player) {
        String additionalPoints = getNextPoint(player.getScore());
        player.setScore(additionalPoints);
    }

    private boolean isDeuce(PlayersDTO player, PlayersDTO opponent) {
        return player.getScore().equals("40") && opponent.getScore().equals("40");
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

    private void resetAfterWinning(PlayersDTO player) {
        player.setScore("0");
    }
}
