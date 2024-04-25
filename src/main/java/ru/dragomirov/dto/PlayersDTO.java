package ru.dragomirov.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayersDTO {
    private int id;
    private String name;
    private int score;
    private boolean advantage;
    private int gamesWon;
}
