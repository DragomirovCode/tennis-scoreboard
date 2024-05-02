package ru.dragomirov.services;

import ru.dragomirov.dao.HibernateMatchesDAO;
import ru.dragomirov.dao.HibernatePlayersDAO;
import ru.dragomirov.dto.MatchesDTO;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.entities.Matches;
import ru.dragomirov.entities.Players;

public class FinishedMatchesPersistenceService {
    private final HibernateMatchesDAO hibernateMatchesDAO;
    private final HibernatePlayersDAO hibernatePlayersDAO;
    private final PlayersService playersService;

    public FinishedMatchesPersistenceService(HibernateMatchesDAO hibernateMatchesDAO,
                                             HibernatePlayersDAO hibernatePlayersDAO,
                                             PlayersService playersService) {
        this.hibernateMatchesDAO = hibernateMatchesDAO;
        this.hibernatePlayersDAO = hibernatePlayersDAO;
        this.playersService = playersService;
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
        MatchesDTO dto = new MatchesDTO();
        dto.setId(match.getId());
        dto.setPlayer1(playersService.toDTO(match.getPlayer1()));
        dto.setPlayer2(playersService.toDTO(match.getPlayer2()));
        dto.setWinner(playersService.toDTO(match.getWinner()));
        return dto;
    }

    public Matches toEntity(MatchesDTO matchesDTO) {
        Matches matches = new Matches();
        matches.setId(matchesDTO.getId());
        matches.setPlayer1(playersService.toEntity(matchesDTO.getPlayer1()));
        matches.setPlayer2(playersService.toEntity(matchesDTO.getPlayer2()));
        matches.setWinner(playersService.toEntity(matchesDTO.getWinner()));
        return matches;
    }
}
