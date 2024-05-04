import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dragomirov.dto.MatchDTO;
import ru.dragomirov.dto.MatchDTOFactory;
import ru.dragomirov.dto.PlayerDTO;
import ru.dragomirov.dto.PlayerDTOFactory;
import ru.dragomirov.services.MatchScoreCalculationService;

public class MatchScoreCalculationServiceTest {
    private MatchDTO setupMatch(String playerScore, int playerGamesWon, int playerSet,
                                String opponentScore, int opponentGamesWon, int opponentSet) {
        PlayerDTOFactory playerDTOFactory = new PlayerDTOFactory();
        PlayerDTO player = playerDTOFactory.createPlayers("player", playerScore);
        player.setGamesWon(playerGamesWon);
        player.setSet(playerSet);
        PlayerDTO opponent = playerDTOFactory.createPlayers("opponent", opponentScore);
        opponent.setGamesWon(opponentGamesWon);
        opponent.setSet(opponentSet);

        MatchDTOFactory matchDTOFactory = new MatchDTOFactory();
        return matchDTOFactory.createMatches(player, opponent);
    }

    @Test
    public void shouldContinueGameAfterDeuce(){
        MatchDTO matches = setupMatch("40", 0, 0,
                "40", 0, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("AD", matches.getPlayer1().getScore());
        Assertions.assertEquals(0, matches.getPlayer1().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer1().getSet());

        Assertions.assertEquals("40", matches.getPlayer2().getScore());
        Assertions.assertEquals(0, matches.getPlayer2().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer2().getSet());
    }

    @Test
    public void shouldWinGameAfterFortyLove(){
        MatchDTO matches = setupMatch("40", 0, 0,
                "0", 0, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("0", matches.getPlayer1().getScore());
        Assertions.assertEquals(1, matches.getPlayer1().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer1().getSet());

        Assertions.assertEquals("0", matches.getPlayer2().getScore());
        Assertions.assertEquals(0, matches.getPlayer2().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer2().getSet());
    }

    @Test
    public void shouldStartTiebreakAtSixAll(){
        MatchDTO matches = setupMatch("0", 6, 0,
                "0", 6, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("1", matches.getPlayer1().getScore());
        Assertions.assertEquals(6, matches.getPlayer1().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer1().getSet());

        Assertions.assertEquals("0", matches.getPlayer2().getScore());
        Assertions.assertEquals(6, matches.getPlayer2().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer2().getSet());
    }

    @Test
    public void shouldStartNewSetAfterEightSix(){
        MatchDTO matches = setupMatch("7", 6, 0,
                "6", 6, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("0", matches.getPlayer1().getScore());
        Assertions.assertEquals(0, matches.getPlayer1().getGamesWon());
        Assertions.assertEquals(1, matches.getPlayer1().getSet());

        Assertions.assertEquals("0", matches.getPlayer2().getScore());
        Assertions.assertEquals(0, matches.getPlayer2().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer2().getSet());
    }

    @Test
    public void shouldWinGameAfterAdvantage(){
        MatchDTO matches = setupMatch("AD", 0, 0,
                "40", 0, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("0", matches.getPlayer1().getScore());
        Assertions.assertEquals(1, matches.getPlayer1().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer1().getSet());

        Assertions.assertEquals("0", matches.getPlayer2().getScore());
        Assertions.assertEquals(0, matches.getPlayer2().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer2().getSet());
    }

    @Test
    public void shouldReturnToDeuceAfterPlayerScores(){
        MatchDTO matches = setupMatch("40", 0, 0,
                "AD", 0, 0);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("40", matches.getPlayer1().getScore());
        Assertions.assertEquals(0, matches.getPlayer1().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer1().getSet());

        Assertions.assertEquals("40", matches.getPlayer2().getScore());
        Assertions.assertEquals(0, matches.getPlayer2().getGamesWon());
        Assertions.assertEquals(0, matches.getPlayer2().getSet());
    }

    @Test
    public void shouldPlayUntilThreeSets(){
        MatchDTO matches = setupMatch("40", 6, 2,
                "0", 6, 2);
        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, matches.getPlayer1(), matches.getPlayer2());

        Assertions.assertEquals("player", matches.getWinner().getName());

        Assertions.assertEquals("0", matches.getPlayer1().getScore());
        Assertions.assertEquals(0, matches.getPlayer1().getGamesWon());
        Assertions.assertEquals(3, matches.getPlayer1().getSet());

        Assertions.assertEquals("0", matches.getPlayer2().getScore());
        Assertions.assertEquals(0, matches.getPlayer2().getGamesWon());
        Assertions.assertEquals(2, matches.getPlayer2().getSet());
    }
}
