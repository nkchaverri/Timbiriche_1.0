import com.timbiriche.controllers.BoxController;
import com.timbiriche.controllers.GameMatrixController;
import com.timbiriche.controllers.PlayerController;
import com.timbiriche.models.Player;

public class Main
{
    public static void main( String[] args )
    {
        GameMatrixController gameMatrixController = new GameMatrixController();
        gameMatrixController.createGameMatrix( 2,2 );

        BoxController boxController = new BoxController( gameMatrixController.getGameMatrix().getMatrix() );
        PlayerController playerController = new PlayerController();
        //llamar primero a la lista de jugadores disponibles

        Player player1 = playerController.createNewPlayer( 111111, "NC" );
        player1.setPoints( 3 );
        Player player2 = playerController.createNewPlayer( 222222, "MM" );
        player2.setPoints( 2 );
        Player player3 = playerController.createNewPlayer( 333333, "LM" );
        player3.setPoints( 3.5 );

        playerController.getAvailablePlayers();

        System.out.println( "EXISTE? :: " + playerController.playerExist( 111111 ) );
        System.out.println( "EXISTE? :: " + playerController.playerExist( 1233445 ) );

        gameMatrixController.completeGameMatrix( boxController );
        gameMatrixController.printGameMatrix(boxController);

        System.out.println( "----------------------------------------------------------------------------" );
        System.out.println( "Below position 2: " + boxController.getBelowPosition( 0,1 ) );
        System.out.println( "Upper Position 2: " + boxController.getUpperPosition( 0,1 ) );
        System.out.println( "Right Position 2: " + boxController.getRightPosition( 0,1 ) );
        System.out.println( "Left Position 2: " + boxController.getLeftPosition( 0,1 ) );


        boxController.markDownSide( boxController.searchBoxById( 1 ),player1 );
        System.out.println( "--------------------------- Position 1 Down side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        boxController.markUppertSide( boxController.searchBoxById( 1 ),player1 );
        System.out.println( "--------------------------- Position 1 upper side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        boxController.markLeftSide( boxController.searchBoxById( 1 ),player2 );
        System.out.println( "--------------------------- Position 1 left side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        boxController.markRightSide( boxController.searchBoxById( 1 ),player3 );
        System.out.println( "--------------------------- Position 1 right side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        System.out.println( "--------------------------- PRINT PLAYERS ----------------------" );
        System.out.println( playerController.getAvailabePlayersList());

    }
}
