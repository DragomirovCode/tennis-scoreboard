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

        switch (currentScore) {
            case 0:
                additionalPoints = 15;
                break;
            case 15:
                additionalPoints = 30;
                break;
            case 30:
                additionalPoints = 40;
                break;
        }

        player.setScore(additionalPoints);
    }

    public int getPlayerScore(PlayersDTO player) {
        return player.getScore();
    }
}
