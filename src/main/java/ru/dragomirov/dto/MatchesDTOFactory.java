package ru.dragomirov.dto;

public class MatchesDTOFactory {
    public MatchesDTO createMatches(PlayersDTO player1, PlayersDTO player2) {
        MatchesDTO matches = new MatchesDTO();
        matches.setPlayer1(player1);
        matches.setPlayer2(player2);
        return matches;
    }
}
