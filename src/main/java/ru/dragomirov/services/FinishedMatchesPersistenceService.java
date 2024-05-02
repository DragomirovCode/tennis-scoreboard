package ru.dragomirov.services;

import org.modelmapper.ModelMapper;
import ru.dragomirov.dao.HibernateMatchesDAO;
import ru.dragomirov.dao.HibernatePlayersDAO;
import ru.dragomirov.dto.MatchesDTO;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.entities.Matches;
import ru.dragomirov.entities.Players;

/**
 * Инкапсулирует запись законченных матчей в БД
 */
public class FinishedMatchesPersistenceService {
    private final HibernateMatchesDAO hibernateMatchesDAO;
    private final HibernatePlayersDAO hibernatePlayersDAO;
    private final PlayersService playersService;
    private final ModelMapper modelMapper;

    public FinishedMatchesPersistenceService() {
        this.hibernateMatchesDAO = new HibernateMatchesDAO();
        this.hibernatePlayersDAO = new HibernatePlayersDAO();
        this.playersService = new PlayersService();
        this.modelMapper = new ModelMapper();
    }

    public void handleSetWin(MatchesDTO match, PlayersDTO player, PlayersDTO opponent) {
        Players playerEntity = playersService.toEntity(player);
        Players opponentEntity = playersService.toEntity(opponent);

        hibernatePlayersDAO.save(playerEntity);
        hibernatePlayersDAO.save(opponentEntity);

        PlayersDTO players1 = playersService.toDTO(playerEntity);
        match.setWinner(players1);

        Matches updatedMatch = toEntity(match);
        updatedMatch.setPlayer1(playerEntity);
        updatedMatch.setPlayer2(opponentEntity);

        hibernateMatchesDAO.save(updatedMatch);
    }

    public MatchesDTO toDTO(Matches match) {
        return modelMapper.map(match, MatchesDTO.class);
    }

    public Matches toEntity(MatchesDTO matchesDTO) {
        return modelMapper.map(matchesDTO, Matches.class);
    }
}
