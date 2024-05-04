package ru.dragomirov.dto;

public class MatchDTOFactory {
    public MatchDTO createMatches(PlayerDTO player1, PlayerDTO player2) {
        MatchDTO matches = new MatchDTO();
        matches.setPlayer1(player1);
        matches.setPlayer2(player2);
        return matches;
    }
}
