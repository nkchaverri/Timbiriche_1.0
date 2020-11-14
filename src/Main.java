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
//
//
//        GameController gameController= new GameController();
//        gameController.intilizePlayers();
//        PlayerController pc = new PlayerController();
//        Player player1 = pc.createNewPlayer( 1001,"LM" );
//        player1.setPoints( 0.5 );
//        Player player2 = pc.createNewPlayer( 1002,"NC" );
//        player2.setPoints( 0.0 );
//        Player player3 = pc.createNewPlayer( 99999999, "COM" );
//        player3.setComputer( true );
//
//        pc.createPlayerFile();
    }
}
