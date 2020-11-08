import com.timbiriche.controllers.GameController;
import com.timbiriche.controllers.PlayerController;
import com.timbiriche.models.Player;
import com.timbiriche.views.GameView;

public class Main
{
    public static void main( String[] args )
    {
        GameView gv = new GameView();
        gv.initializeGame();

//        GameController gameController= new GameController();
//        gameController.intilizePlayers();
//        PlayerController pc = new PlayerController();
//        Player player1 = pc.getPlayerById( 333333 );
//        Player player2 = pc.getPlayerById(222222);
//        gameController.twoPlayersGame( player1,player2  );

    }
}
