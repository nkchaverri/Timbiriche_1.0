import com.timbiriche.controllers.BoxController;
import com.timbiriche.controllers.GameMatrixController;

public class Main
{
    public static void main( String[] args )
    {
        GameMatrixController gameMatrixController = new GameMatrixController();
        gameMatrixController.createGameMatrix( 2,2 );

        BoxController boxController = new BoxController( gameMatrixController.getGameMatrix().getMatrix() );
        gameMatrixController.completeGameMatrix( boxController );
        gameMatrixController.printGameMatrix(boxController);

        System.out.println( "----------------------------------------------------------------------------" );
        System.out.println( "Below position 2: " + boxController.getBelowPosition( 0,1 ) );
        System.out.println( "Upper Position 2: " + boxController.getUpperPosition( 0,1 ) );
        System.out.println( "Right Position 2: " + boxController.getRightPosition( 0,1 ) );
        System.out.println( "Left Position 2: " + boxController.getLeftPosition( 0,1 ) );


//        boxController.markLeftSide( boxController.searchBoxById( 2 ) );
//        System.out.println( "--------------------------- Position 2 left side ----------------------" );
//        gameMatrixController.printGameMatrix(boxController);
//
//        boxController.markLeftSide( boxController.searchBoxById( 1 ) );
//        System.out.println( "--------------------------- Position 1 left side ----------------------" );
//        gameMatrixController.printGameMatrix(boxController);
//
//        boxController.markRightSide( boxController.searchBoxById( 2 ) );
//        System.out.println( "--------------------------- Position 2 right side ----------------------" );
//        gameMatrixController.printGameMatrix(boxController);
//
//        boxController.markRightSide( boxController.searchBoxById( 3 ) );
//        System.out.println( "--------------------------- Position 3 right side ----------------------" );
//        gameMatrixController.printGameMatrix(boxController);
//
//        boxController.markRightSide( boxController.searchBoxById( 4 ) );
//        System.out.println( "--------------------------- Position 4 right side ----------------------" );
//        gameMatrixController.printGameMatrix(boxController);

        boxController.markDownSide( boxController.searchBoxById( 2 ) );
        System.out.println( "--------------------------- Position 2 Down side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        boxController.markUppertSide( boxController.searchBoxById( 1 ) );
        System.out.println( "--------------------------- Position 1 upper side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

        boxController.markUppertSide( boxController.searchBoxById( 3 ) );
        System.out.println( "--------------------------- Position 1 upper side ----------------------" );
        gameMatrixController.printGameMatrix(boxController);

    }
}
