package ru.dragomirov.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchDTO {
    private int id;
    private PlayerDTO player1;
    private PlayerDTO player2;
    private PlayerDTO winner;
}
