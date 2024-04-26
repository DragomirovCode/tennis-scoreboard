package ru.dragomirov.dto;

public class PlayersDTOFactory {
    public PlayersDTO createPlayers(String name, String score) {
        PlayersDTO playersDTO = new PlayersDTO();
        playersDTO.setName(name);
        playersDTO.setScore(score);
        return playersDTO;
    }
}
