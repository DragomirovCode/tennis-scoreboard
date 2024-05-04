package ru.dragomirov.dto;

public class PlayerDTOFactory {
    public PlayerDTO createPlayers(String name, String score) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(name);
        playerDTO.setScore(score);
        return playerDTO;
    }
}
