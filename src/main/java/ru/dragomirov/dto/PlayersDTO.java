package ru.dragomirov.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayersDTO {
    private int id;
    private String name;
    private String score;
    private int gamesWon;
    private int set;
}
