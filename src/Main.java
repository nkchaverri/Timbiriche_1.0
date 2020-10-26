import com.timbiriche.controllers.BoxController;
import com.timbiriche.controllers.GameMatrixController;

public class Main
{
    public static void main( String[] args )
    {
        GameMatrixController gameMatrixController = new GameMatrixController();

        gameMatrixController.createGameMatrix( 3,7 );
        gameMatrixController.completeGameMatrix();
        gameMatrixController.printGameMatrix();

//        System.out.println( "Below position 1: " + gameMatrixController.getBelowPosition( 0,0 ) );
//        System.out.println( "Upper Position 1: " + gameMatrixController.getUpperPosition( 0,0 ) );
//        System.out.println( "Right Position 1: " + gameMatrixController.getRightPosition( 0,0 ) );
//        System.out.println( "Left Position 1: " + gameMatrixController.getLeftPosition( 0,0 ) );
//        System.out.println( "----------------------------------------------------------------------------" );
//        System.out.println( "Below position 2: " + gameMatrixController.getBelowPosition( 0,1 ) );
//        System.out.println( "Upper Position 2: " + gameMatrixController.getUpperPosition( 0,1 ) );
//        System.out.println( "Right Position 2: " + gameMatrixController.getRightPosition( 0,1 ) );
//        System.out.println( "Left Position 2: " + gameMatrixController.getLeftPosition( 0,1 ) );
//        System.out.println( "----------------------------------------------------------------------------" );
        System.out.println( "Below position 10: " + gameMatrixController.getBelowPosition( 1,2 ) );
        System.out.println( "Upper Position 10: " + gameMatrixController.getUpperPosition( 1,2 ) );
        System.out.println( "Right Position 10: " + gameMatrixController.getRightPosition( 1,2 ) );
        System.out.println( "Left Position 10: " + gameMatrixController.getLeftPosition( 1,2 ) );
    }
}
