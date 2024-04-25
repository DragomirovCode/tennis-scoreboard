package ru.dragomirov.dto;

public class PlayersDTOFactory {
    public PlayersDTO createPlayers(String name, int score) {
        PlayersDTO playersDTO = new PlayersDTO();
        playersDTO.setName(name);
        playersDTO.setScore(score);
        return playersDTO;
    }
}
