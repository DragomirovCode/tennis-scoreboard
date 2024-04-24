package ru.dragomirov.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchesDTO {
    private int id;
    private PlayersDTO player1;
    private PlayersDTO player2;
    private String score;
    private PlayersDTO winner;
}
