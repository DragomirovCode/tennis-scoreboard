package ru.dragomirov.services;

import ru.dragomirov.dto.PlayersDTO;

public class MatchesService {
    public void addPointsToPlayers(PlayersDTO player, PlayersDTO opponent) {
        if (player.getScore() == 40 && opponent.getScore() < 40) {
            winGame(player, opponent);
            return;
        }

        if (isDeuce(player, opponent)) {
            handleDeuce(player, opponent);
            return;
        }

        if (player.isAdvantage()) {
            winGame(player, opponent);
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
            opponent.setScore(0);
        }
        resetAfterWinning(player);
    }

    private void handleDeuce(PlayersDTO player, PlayersDTO opponent) {
        if (player.isAdvantage()) {
            winGame(player, opponent);
        } else if (opponent.isAdvantage()) {
            opponent.setAdvantage(false);
            player.setAdvantage(true);
        } else {
            player.setAdvantage(true);
        }
    }

    private void addPoint(PlayersDTO player) {
        int additionalPoints = getNextPoint(player.getScore());
        player.setScore(additionalPoints);
    }

    private boolean isDeuce(PlayersDTO player, PlayersDTO opponent) {
        return player.getScore() == 40 && opponent.getScore() == 40;
    }

    private int getNextPoint(int currentPoints) {
        switch (currentPoints) {
            case 0:
                return 15;
            case 15:
                return 30;
            case 30:
                return 40;
            default:
                return 0;
        }
    }

    private int getNextGameCount(int countScore) {
        switch (countScore) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            default:
                return 0;
        }
    }

    private void resetAfterWinning(PlayersDTO player) {
        player.setScore(0);
        player.setAdvantage(false);
    }
}