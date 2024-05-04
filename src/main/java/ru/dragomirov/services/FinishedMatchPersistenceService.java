package ru.dragomirov.services;

import org.modelmapper.ModelMapper;
import ru.dragomirov.dao.HibernateMatchDAO;
import ru.dragomirov.dao.HibernatePlayerDAO;
import ru.dragomirov.dto.MatchDTO;
import ru.dragomirov.dto.PlayerDTO;
import ru.dragomirov.entities.Match;
import ru.dragomirov.entities.Player;

/**
 * FinishedMatchPersistenceService инкапсулирует запись законченных матчей в БД
 */
public class FinishedMatchPersistenceService {
    private final HibernateMatchDAO hibernateMatchDAO;
    private final HibernatePlayerDAO hibernatePlayerDAO;
    private final PlayersService playersService;
    private final ModelMapper modelMapper;

    public FinishedMatchPersistenceService() {
        this.hibernateMatchDAO = new HibernateMatchDAO();
        this.hibernatePlayerDAO = new HibernatePlayerDAO();
        this.playersService = new PlayersService();
        this.modelMapper = new ModelMapper();
    }

    public void handleSetWin(MatchDTO match, PlayerDTO player, PlayerDTO opponent) {
        Player playerEntity = playersService.toEntity(player);
        Player opponentEntity = playersService.toEntity(opponent);

        hibernatePlayerDAO.save(playerEntity);
        hibernatePlayerDAO.save(opponentEntity);

        PlayerDTO players1 = playersService.toDTO(playerEntity);
        match.setWinner(players1);

        Match updatedMatch = toEntity(match);
        updatedMatch.setPlayer1(playerEntity);
        updatedMatch.setPlayer2(opponentEntity);

        hibernateMatchDAO.save(updatedMatch);
    }

    public MatchDTO toDTO(Match match) {
        return modelMapper.map(match, MatchDTO.class);
    }

    public Match toEntity(MatchDTO matchDTO) {
        return modelMapper.map(matchDTO, Match.class);
    }
}
