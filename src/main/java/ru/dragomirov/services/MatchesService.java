package ru.dragomirov.services;

import ru.dragomirov.dto.PlayersDTO;

public class MatchesService {
    public void addPointsToPlayers(PlayersDTO player) {
        int currentScore = player.getScore();
        int additionalPoints = 0;

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
