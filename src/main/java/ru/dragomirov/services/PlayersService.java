package ru.dragomirov.services;

import org.modelmapper.ModelMapper;
import ru.dragomirov.dao.HibernateMatchesDAO;
import ru.dragomirov.dao.HibernatePlayersDAO;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.entities.Players;

public class PlayersService {
    private final HibernateMatchesDAO hibernateMatchesDAO;
    private final HibernatePlayersDAO hibernatePlayersDAO;
    private final ModelMapper modelMapper;

    public PlayersService() {
        this.hibernateMatchesDAO = new HibernateMatchesDAO();
        this.hibernatePlayersDAO = new HibernatePlayersDAO();
        this.modelMapper = new ModelMapper();
    }

    public PlayersDTO toDTO(Players player) {
        return modelMapper.map(player, PlayersDTO.class);
    }

    public Players toEntity(PlayersDTO dto) {
        return modelMapper.map(dto, Players.class);
    }
}
