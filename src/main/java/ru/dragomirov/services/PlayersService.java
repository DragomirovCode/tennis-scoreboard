package ru.dragomirov.services;

import ru.dragomirov.dao.MatchesDAO;
import ru.dragomirov.dao.PlayersDAO;

public class PlayersService {
    private final MatchesDAO matchesDAO;
    private final PlayersDAO playersDAO;

    public PlayersService(MatchesDAO matchesDAO, PlayersDAO playersDAO) {
        this.matchesDAO = matchesDAO;
        this.playersDAO = playersDAO;
    }

    public PlayersDTO toDTO(Players player) {
        PlayersDTO dto = new PlayersDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        return dto;
    }

    public Players toEntity(PlayersDTO dto) {
        Players player = new Players();
        player.setId(dto.getId());
        player.setName(dto.getName());
        return player;
    }
}
