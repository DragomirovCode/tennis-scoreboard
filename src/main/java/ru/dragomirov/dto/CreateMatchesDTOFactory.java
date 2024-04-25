package ru.dragomirov.dto;

import ru.dragomirov.commons.ScoreManager;

public class CreateMatchesDTOFactory {
    private ScoreManager scoreManager;

    public CreateMatchesDTOFactory() {
        this.scoreManager = new ScoreManager();
    }

    public MatchesDTO createMatches(PlayersDTO player1, PlayersDTO player2, String score) {
        MatchesDTO matches = new MatchesDTO();
        matches.setPlayer1(player1);
        matches.setPlayer2(player2);
        matches.setScore(score);
        return matches;
    }

    public void addPointsToPlayers1(PlayersDTO player1) {
        scoreManager.addPoints(player1, 1); // Добавьте 1 очко игроку 1
    }

    public void addPointsToPlayers2(PlayersDTO player2) {
        scoreManager.addPoints(player2, 1); // Добавьте 1 очко игроку 2
    }

    public int getPlayerScore(PlayersDTO player) {
        return scoreManager.getPoints(player);
    }
}
