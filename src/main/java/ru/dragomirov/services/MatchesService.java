package ru.dragomirov.services;

import ru.dragomirov.dao.HibernateMatchesDAO;
import ru.dragomirov.dao.HibernatePlayersDAO;
import ru.dragomirov.dto.MatchesDTO;
import ru.dragomirov.dto.MatchesDTOFactory;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.entities.Matches;
import ru.dragomirov.entities.Players;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class MatchesService {
    private final HibernateMatchesDAO hibernateMatchesDAO;
    private final HibernatePlayersDAO hibernatePlayersDAO;
    private final PlayersService playersService;
    private final MatchesDTOFactory matchesDTOFactory;
    private final Map<String, BiConsumer<PlayersDTO, PlayersDTO>> scoreHandlers = new HashMap<>();

    {
        scoreHandlers.put("40:30", this::winGameAndReset);
        scoreHandlers.put("40:15", this::winGameAndReset);
        scoreHandlers.put("40:0",  this::winGameAndReset);
        scoreHandlers.put("40:40", this::handleDeuce);
        scoreHandlers.put("40:AD", this::handleDeuce);
        scoreHandlers.put("AD:40", this::winGameAndReset);
        scoreHandlers.put("AD:AD", this::winGameAndReset);
    }

    public MatchesService() {
        this.hibernateMatchesDAO = new HibernateMatchesDAO();
        this.hibernatePlayersDAO = new HibernatePlayersDAO();
        this.playersService = new PlayersService();
        this.matchesDTOFactory = new MatchesDTOFactory();
    }

    public void addPointsToPlayers(MatchesDTO match, PlayersDTO player, PlayersDTO opponent) {
        if (player.getGamesWon() == 6 && opponent.getGamesWon() == 6) {
            handleTieBreak(match, player, opponent);
            return;
        }

        String scoreKey = player.getScore() + ":" + opponent.getScore();
        if (scoreHandlers.containsKey(scoreKey)) {
            scoreHandlers.get(scoreKey).accept(player, opponent);
        } else {
            addPoint(player);
        } if (player.getSet() == 1) {
            handleSetWin(match, player, opponent);
        }
    }

    private void winGameAndReset(PlayersDTO player, PlayersDTO opponent) {
        winGame(player, opponent);
        resetScoreAfterWinning(player, opponent);
    }

    private void winGame(PlayersDTO player, PlayersDTO opponent) {
        player.setGamesWon(getNextGameCount(player.getGamesWon()));
        if (player.getGamesWon() >= 6 && (player.getGamesWon() - opponent.getGamesWon() >= 2)) {
            player.setSet(player.getSet() + 1);
            resetGamesAfterWinning(player, opponent);
        }
    }

    private void handleDeuce(PlayersDTO player, PlayersDTO opponent) {
        if (player.getScore().equals("AD")) {
            winGame(player, opponent);
            resetScoreAfterWinning(player, opponent);
        } else if (opponent.getScore().equals("AD")) {
            opponent.setScore("40");
            player.setScore("AD");
        } else {
            player.setScore("AD");
        }
    }

    private void handleTieBreak(MatchesDTO match, PlayersDTO player, PlayersDTO opponent) {
        player.setScore(String.valueOf(Integer.parseInt(player.getScore()) + 1));
        if (Integer.parseInt(player.getScore()) >= 7 &&
                (Integer.parseInt(player.getScore()) - Integer.parseInt(opponent.getScore()) >= 2)) {
            player.setSet(player.getSet() + 1);
            resetGamesAfterWinning(player, opponent);
            resetScoreAfterWinning(player, opponent);
            handleSetWin(match, player, opponent);
        }
    }

    private void addPoint(PlayersDTO player) {
        String additionalPoints = getNextPoint(player.getScore());
        player.setScore(additionalPoints);
    }

    private String getNextPoint(String currentPoints) {
        switch (currentPoints) {
            case "0":
                return "15";
            case "15":
                return "30";
            case "30":
                return "40";
        }
        return currentPoints;
    }

    private int getNextGameCount(int countScore) {
        return countScore + 1;
    }

    private void resetScoreAfterWinning(PlayersDTO player, PlayersDTO opponent) {
        player.setScore("0");
        opponent.setScore("0");
    }

    private void resetGamesAfterWinning(PlayersDTO player, PlayersDTO opponent) {
        player.setGamesWon(0);
        opponent.setGamesWon(0);
    }

    private void handleSetWin(MatchesDTO match, PlayersDTO player, PlayersDTO opponent) {
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
