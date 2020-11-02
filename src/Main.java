import com.timbiriche.controllers.BoxController;
import com.timbiriche.controllers.GameMatrixController;
import com.timbiriche.controllers.PlayerController;
import com.timbiriche.models.Player;

import com.timbiriche.Utils.FileUtils;

public class Main
{
    public static void main( String[] args )
    {
        GameMatrixController gameMatrixController = new GameMatrixController();
        gameMatrixController.createGameMatrix( 2,2 );

        BoxController boxController = new BoxController( gameMatrixController.getGameMatrix().getMatrix() );
        PlayerController playerController = new PlayerController();


        System.out.println( "EXISTE? :: " + playerController.playerExist( 111111 ) );
        System.out.println( "EXISTE? :: " + playerController.playerExist( 1233445 ) );

        gameMatrixController.completeGameMatrix( boxController );
        gameMatrixController.printGameMatrix(boxController);

        System.out.println( "----------------------------------------------------------------------------" );
        System.out.println( "Below position 2: " + boxController.getBelowPosition( 0,1 ) );
        System.out.println( "Upper Position 2: " + boxController.getUpperPosition( 0,1 ) );
        System.out.println( "Right Position 2: " + boxController.getRightPosition( 0,1 ) );
        System.out.println( "Left Position 2: " + boxController.getLeftPosition( 0,1 ) );


        boxController.markDownSide( boxController.searchBoxById( 1 ),playerController.getAvailablePlayers()[0] );
        System.out.println( "--------------------------- Position 1 Down side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        boxController.markUppertSide( boxController.searchBoxById( 1 ),playerController.getAvailablePlayers()[0]  );
        System.out.println( "--------------------------- Position 1 upper side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        boxController.markLeftSide( boxController.searchBoxById( 1 ),playerController.getAvailablePlayers()[0]  );
        System.out.println( "--------------------------- Position 1 left side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        boxController.markRightSide( boxController.searchBoxById( 1 ),playerController.getAvailablePlayers()[0]  );
        System.out.println( "--------------------------- Position 1 right side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        System.out.println( "--------------------------- PRINT PLAYERS ----------------------" );
        System.out.println( playerController.getAvailabePlayersList());


        System.out.println( "--------------------------- PRINT AVAILABLE POSITIONS ----------------------" );
        for ( int i = 0; i <boxController.getAvailableBoxes().length ; i++ )
        {
            System.out.println( " Box ID " + boxController.getAvailableBoxes()[i].getBoxId() );

            char[] positions = boxController.getAvailablePositions(boxController.getAvailableBoxes()[i]);

            for ( int j = 0; j <positions.length; j++ )
            {
                if ( positions[j]!= ' ' )
                System.out.println( "Position " + positions[j]  );
            }
        }


    }
}
