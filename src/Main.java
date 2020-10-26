import com.timbiriche.controllers.GameMatrixController;

public class Main
{
    public static void main( String[] args )
    {
        GameMatrixController gameMatrixController = new GameMatrixController();

        gameMatrixController.createGameMatrix( 2,2 );
        gameMatrixController.completeGameMatrix();
        gameMatrixController.printGameMatrix();
    }
}
