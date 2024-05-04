package ru.dragomirov.services;

import org.modelmapper.ModelMapper;
import ru.dragomirov.dto.PlayerDTO;
import ru.dragomirov.entities.Player;

/**
 * PlayersService обеспечивает преобразование между объектами модели "Player"
 * и их представлениями в виде DTO (Data Transfer Object).
 */
public class PlayersService {
    private final ModelMapper modelMapper;

    public PlayersService() {
        this.modelMapper = new ModelMapper();
    }

    public PlayerDTO toDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    public Player toEntity(PlayerDTO dto) {
        return modelMapper.map(dto, Player.class);
    }
}
