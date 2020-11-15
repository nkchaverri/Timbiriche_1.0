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
    }
}
