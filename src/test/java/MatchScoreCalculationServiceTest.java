import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dragomirov.dto.MatchesDTO;
import ru.dragomirov.dto.MatchesDTOFactory;
import ru.dragomirov.dto.PlayersDTO;
import ru.dragomirov.dto.PlayersDTOFactory;
import ru.dragomirov.services.MatchScoreCalculationService;

public class MatchScoreCalculationServiceTest {
    @Test
    public void shouldContinueGameAfterDeuce(){
        PlayersDTOFactory playersDTOFactory = new PlayersDTOFactory();
        PlayersDTO player = playersDTOFactory.createPlayers("player", "40");
        PlayersDTO opponent = playersDTOFactory.createPlayers("opponent", "40");

        MatchesDTOFactory matchesDTOFactory = new MatchesDTOFactory();
        MatchesDTO matches = matchesDTOFactory.createMatches(player, opponent);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, player, opponent);

        Assertions.assertEquals("AD", player.getScore());
        Assertions.assertEquals(0, player.getGamesWon());
    }

    @Test
    public void shouldWinGameAfterFortyLove(){
        PlayersDTOFactory playersDTOFactory = new PlayersDTOFactory();
        PlayersDTO player = playersDTOFactory.createPlayers("player", "40");
        PlayersDTO opponent = playersDTOFactory.createPlayers("opponent", "0");

        MatchesDTOFactory matchesDTOFactory = new MatchesDTOFactory();
        MatchesDTO matches = matchesDTOFactory.createMatches(player, opponent);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, player, opponent);

        Assertions.assertEquals("0", player.getScore());
        Assertions.assertEquals(1, player.getGamesWon());
    }

    @Test
    public void shouldStartTiebreakAtSixAll(){
        PlayersDTOFactory playersDTOFactory = new PlayersDTOFactory();
        PlayersDTO player = playersDTOFactory.createPlayers("player", "0");
        player.setGamesWon(6);
        PlayersDTO opponent = playersDTOFactory.createPlayers("opponent", "0");
        opponent.setGamesWon(6);

        MatchesDTOFactory matchesDTOFactory = new MatchesDTOFactory();
        MatchesDTO matches = matchesDTOFactory.createMatches(player, opponent);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, player, opponent);

        Assertions.assertEquals("1", player.getScore());
    }

    @Test
    public void shouldStartNewSetAfterEightSix(){
        PlayersDTOFactory playersDTOFactory = new PlayersDTOFactory();
        PlayersDTO player = playersDTOFactory.createPlayers("player", "7");
        player.setGamesWon(6);
        PlayersDTO opponent = playersDTOFactory.createPlayers("opponent", "6");
        opponent.setGamesWon(6);

        MatchesDTOFactory matchesDTOFactory = new MatchesDTOFactory();
        MatchesDTO matches = matchesDTOFactory.createMatches(player, opponent);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, player, opponent);

        Assertions.assertEquals(1, player.getSet());
        Assertions.assertEquals("0", player.getScore());
    }

    @Test
    public void shouldWinGameAfterAdvantage(){
        PlayersDTOFactory playersDTOFactory = new PlayersDTOFactory();
        PlayersDTO player = playersDTOFactory.createPlayers("player", "AD");
        PlayersDTO opponent = playersDTOFactory.createPlayers("opponent", "40");

        MatchesDTOFactory matchesDTOFactory = new MatchesDTOFactory();
        MatchesDTO matches = matchesDTOFactory.createMatches(player, opponent);

        MatchScoreCalculationService service = new MatchScoreCalculationService();
        service.addPointsToPlayers(matches, player, opponent);

        Assertions.assertEquals(1, player.getGamesWon());
        Assertions.assertEquals("0", player.getScore());
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
