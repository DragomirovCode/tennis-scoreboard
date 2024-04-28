package ru.dragomirov.services;

import ru.dragomirov.dao.MatchesDAO;
import ru.dragomirov.dao.PlayersDAO;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.entities.Players;

public class PlayersService {
    private final MatchesDAO matchesDAO;
    private final PlayersDAO playersDAO;
    private final MatchesService matchesService;

    public PlayersService() {
        this.matchesDAO = new MatchesDAO();
        this.playersDAO = new PlayersDAO();
        this.matchesService = new MatchesService();
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
