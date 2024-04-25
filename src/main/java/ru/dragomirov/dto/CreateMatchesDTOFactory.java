package ru.dragomirov.dto;

public class CreateMatchesDTOFactory {
    public MatchesDTO createMatches(PlayersDTO player1, PlayersDTO player2, String score) {
        MatchesDTO matches = new MatchesDTO();
        matches.setPlayer1(player1);
        matches.setPlayer2(player2);
        matches.setScore(score);
        return matches;
    }
}
