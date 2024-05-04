import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dragomirov.dto.MatchesDTO;
import ru.dragomirov.dto.MatchesDTOFactory;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.dto.PlayersDTOFactory;
import ru.dragomirov.services.MatchScoreCalculationService;

public class MatchScoreCalculationServiceTest {
    private MatchesDTO setupMatch(String playerScore, int playerGamesWon, int playerSet,
                                  String opponentScore, int opponentGamesWon, int opponentSet) {
        PlayersDTOFactory playersDTOFactory = new PlayersDTOFactory();
        PlayersDTO player = playersDTOFactory.createPlayers("player", playerScore);
        player.setGamesWon(playerGamesWon);
        player.setSet(playerSet);
        PlayersDTO opponent = playersDTOFactory.createPlayers("opponent", opponentScore);
        opponent.setGamesWon(opponentGamesWon);
        opponent.setSet(opponentSet);

        MatchesDTOFactory matchesDTOFactory = new MatchesDTOFactory();
        return matchesDTOFactory.createMatches(player, opponent);
    }

    @Test
    public void shouldContinueGameAfterDeuce(){
        MatchesDTO matches = setupMatch("40", 0, 0,
                "40", 0, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("AD", matches.getPlayer1().getScore());
        Assertions.assertEquals(0, matches.getPlayer1().getGamesWon());
    }

    @Test
    public void shouldWinGameAfterFortyLove(){
        MatchesDTO matches = setupMatch("40", 0, 0,
                "0", 0, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("0", matches.getPlayer1().getScore());
        Assertions.assertEquals(1, matches.getPlayer1().getGamesWon());
    }

    @Test
    public void shouldStartTiebreakAtSixAll(){
        MatchesDTO matches = setupMatch("0", 6, 0,
                "0", 6, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("1", matches.getPlayer1().getScore());
    }

    @Test
    public void shouldStartNewSetAfterEightSix(){
        MatchesDTO matches = setupMatch("7", 6, 0,
                "6", 6, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals(1, matches.getPlayer1().getSet());
        Assertions.assertEquals("0", matches.getPlayer1().getScore());
    }

    @Test
    public void shouldWinGameAfterAdvantage(){
        MatchesDTO matches = setupMatch("AD", 0, 0,
                "40", 0, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals(1, matches.getPlayer1().getGamesWon());
        Assertions.assertEquals("0", matches.getPlayer1().getScore());
    }

    @Test
    public void shouldReturnToDeuceAfterPlayerScores(){
        PlayersDTOFactory playersDTOFactory = new PlayersDTOFactory();
        PlayersDTO player = playersDTOFactory.createPlayers("player", "40");
        PlayersDTO opponent = playersDTOFactory.createPlayers("opponent", "AD");

        MatchesDTOFactory matchesDTOFactory = new MatchesDTOFactory();
        MatchesDTO matches = matchesDTOFactory.createMatches(player, opponent);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, player, opponent);

        Assertions.assertEquals("40", player.getScore());
        Assertions.assertEquals("40", opponent.getScore());
    }
}
