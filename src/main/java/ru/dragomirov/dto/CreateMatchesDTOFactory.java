package ru.dragomirov.dto;

public class CreateMatchesDTOFactory {

    public MatchesDTO createMatches(PlayersDTO player1, PlayersDTO player2, String score) {
        MatchesDTO matches = new MatchesDTO();
        matches.setPlayer1(player1);
        matches.setPlayer2(player2);
        matches.setScore(score);
        return matches;
    }

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
