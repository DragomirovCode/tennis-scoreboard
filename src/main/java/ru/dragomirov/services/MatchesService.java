package ru.dragomirov.services;

import ru.dragomirov.dto.PlayersDTO;

public class MatchesService {
    public void addPointsToPlayers(PlayersDTO player, PlayersDTO opponent) {
        if (player.getScore() == 40 && opponent.getScore() < 40) {
            player.setGamesWon(calculateAdditionalWin(player.getGamesWon()));
            winGame(player);
            return;
        }

        if (isDeuce(player, opponent)) {
            if (player.isAdvantage()) {
                winGame(player);
            } else {
                player.setAdvantage(true);
            }
            return;
        }

        if (player.isAdvantage()) {
            winGame(player);
        } else {
            int additionalPoints = calculateAdditionalPoints(player.getScore());
            player.setScore(additionalPoints);
        }
    }

    private boolean isDeuce(PlayersDTO player, PlayersDTO opponent) {
        return player.getScore() == 40 && opponent.getScore() == 40 && !player.isAdvantage() && !opponent.isAdvantage();
    }

    private int calculateAdditionalPoints(int currentPoints) {
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

    private int calculateAdditionalWin(int countScore) {
        switch (countScore) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                return 0;
        }
    }

    public void winGame(PlayersDTO player) {
        player.setScore(0);
        player.setAdvantage(false);
    }
}
