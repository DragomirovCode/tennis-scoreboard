package ru.dragomirov.services;

import ru.dragomirov.dto.PlayersDTO;

public class MatchesService {
    public void addPointsToPlayers(PlayersDTO player, PlayersDTO opponent) {
        int currentScore = player.getScore();

        if (currentScore == 40 && opponent.getScore() < 40) {
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
            int additionalPoints = calculateAdditionalPoints(currentScore);
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

        player.setScore(additionalPoints);
    }

    public int getPlayerScore(PlayersDTO player) {
        return player.getScore();
    }
}
