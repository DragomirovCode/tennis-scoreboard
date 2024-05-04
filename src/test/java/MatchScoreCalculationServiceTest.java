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

        Assertions.assertEquals(1, player.getGamesWon());
    }
}
