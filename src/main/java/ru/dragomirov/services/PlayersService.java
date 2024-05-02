package ru.dragomirov.services;

import org.modelmapper.ModelMapper;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.entities.Players;

public class PlayersService {
    private final ModelMapper modelMapper;

    public PlayersService() {
        this.modelMapper = new ModelMapper();
    }

    public PlayersDTO toDTO(Players player) {
        return modelMapper.map(player, PlayersDTO.class);
    }

    public Players toEntity(PlayersDTO dto) {
        return modelMapper.map(dto, Players.class);
    }
}
