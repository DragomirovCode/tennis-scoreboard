package ru.dragomirov.dto;

public class CreateMatchesDTOFactory {

    public MatchesDTO createMatches(PlayersDTO player1, PlayersDTO player2, String score) {
        MatchesDTO matches = new MatchesDTO();
        matches.setPlayer1(player1);
        matches.setPlayer2(player2);
        matches.setScore(score);
        return matches;
    }

    public void addPointsToPlayers1(PlayersDTO player1) {
        int currentScore = player1.getScore();
        player1.setScore(currentScore + 1);
    }

    public void addPointsToPlayers2(PlayersDTO player2) {
        int currentScore = player2.getScore();
        player2.setScore(currentScore + 1);
    }

    public int getPlayerScore(PlayersDTO player) {
        return player.getScore();
    }
}
