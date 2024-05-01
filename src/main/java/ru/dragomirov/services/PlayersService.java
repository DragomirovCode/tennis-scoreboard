package ru.dragomirov.services;

import ru.dragomirov.dao.HibernateMatchesDAO;
import ru.dragomirov.dao.PlayersDAO;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.entities.Players;

public class PlayersService {
    private final HibernateMatchesDAO hibernateMatchesDAO;
    private final PlayersDAO playersDAO;

    public PlayersService() {
        this.hibernateMatchesDAO = new HibernateMatchesDAO();
        this.playersDAO = new PlayersDAO();
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
