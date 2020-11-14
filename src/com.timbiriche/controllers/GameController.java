package com.timbiriche.controllers;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.timbiriche.models.Box;
import com.timbiriche.models.Player;

public class GameController
{
    private GameMatrixController gameMatrixController;
    private BoxController boxController;
    private PlayerController playerController;

    public void intilizePlayers(){
        this.playerController = new PlayerController();
    }

    public String showAvailablePlayers(){
       return this.playerController.getAvailabePlayersList();
    }

    public PlayerController getPlayerController()
    {
        return playerController;
    }

    public void initAndFillGameBoard( int rows, int cols){

        this.gameMatrixController = new GameMatrixController( rows, cols );
        BoxController.boxMatrix = gameMatrixController.getGameMatrix().getMatrix();
        this.gameMatrixController.completeGameMatrix();
    }

    public BoxController getBoxController()
    {
        return boxController;
    }

    public void printMatrix(){
        this.gameMatrixController.printMatrix( BoxController.boxMatrix );
    }

    /**
     * Shows all available sides changing english chars
     * to spanish names
     * @param box
     * @return
     */
    public String showAvailableSides( Box box ){
        char[] positions= BoxController.getAvailablePositions( box );
        String result = "";

        for ( int i = 0; i <positions.length ; i++ )
        {
            if ( positions[i]!= ' ' ){
                result+= positions[i]=='L'?"I :Izquierda.\t":"";
                result+= positions[i]=='R'?"D :Derecha.\t":"";
                result+= positions[i]=='U'?"S :Superior.\t":"";
                result+= positions[i]=='D'?"A :Abajo.":"";
            }
        }
        return result;
    }

    /**
     * returns a string with all the available positions
     * @return
     */
    public String availablePositionsList(){
        Box[] boxesAvailable = BoxController.getAvailableBoxes();
        String result = "Posiciones Disponibles: \n" ;
        for ( int i = 0; i <boxesAvailable.length ; i++ )
        {
            result+= boxesAvailable[i].getBoxId() + "\t";
        }
        return result;
    }

    public int[] getBoxesPositions(){
        Box[] boxArray = BoxController.getAvailableBoxes();
        int[] positions = new int[boxArray.length];

        for ( int i = 0; i <positions.length ; i++ )
        {
            positions[i]= boxArray[i].getBoxId();
        }

        return positions;
    }

    public char[] getPosiblePositions(){
        char[] postions= {'I','D','S','A'};
        return postions;
    }

    public boolean areAvailablePositions(){
        return BoxController.getAvailableBoxes().length>0;
    }

    /**
     * creates a move when position and side are valid
     * @param currentPlayer
     * @param box
     * @param position
     * @param side
     * @return
     */
    public boolean createMove( Player currentPlayer,Box box, int position, char side){
        if ( !validSide( side,box )|| !validPosition( position ) ){
            return false;
        }

        markMove( side, box,currentPlayer );
        if ( box.getAssignee() != null || (BoxController.nextBox != null && BoxController.nextBox.getAssignee() != null) ){
            return false;
        }

        return true;
    }
    public boolean createHardMove( Player currentPlayer,Box box, int position, char side){
        boolean madeMove = false;
        if ( !validSide( side,box )|| !validPosition( position ) ){
            return madeMove;
        }

        markMove( side, box,currentPlayer );
        madeMove = true;

        if ( box.getAssignee() == null || (BoxController.nextBox != null && BoxController.nextBox.getAssignee() == null) ){
            return madeMove;
        }

        do
        {
            Box[] boxesToPoint =BoxController.getBoxThreeSides();
            if ( boxesToPoint.length > 0 ){

                for ( int i = 0; i <boxesToPoint.length ; i++ )
                {
                    Box currentBox = boxesToPoint[i];
                    char currentSide = BoxController.getLastSideAvailable( currentBox );

                    markMove(currentSide,currentBox,currentPlayer );
                    System.out.println("Caja :" + currentBox.getBoxId() + " Lado" + convertCharToName( currentSide ));
                }
                madeMove = true;
            }
        }while ( BoxController.getBoxThreeSides().length >0  );

        return madeMove;
    }

    /**
     * mark move according to the side selected
     * @param side
     * @param box
     * @param player
     */
    private void markMove(char side, Box box, Player player){
        switch ( side ){
            case 'L': BoxController.markLeftSide( box,player );
                break;
            case 'R': BoxController.markRightSide( box,player );
                break;
            case 'U': BoxController.markUppertSide( box,player );
                break;
            case 'D': BoxController.markDownSide( box,player );
                break;
        }
    }

    /**
     * convert char from the UI to the one used in the
     * backend
     * @param side
     * @return
     */
    public static char convertChar( char side){
        switch ( side ){
            case 'I': side = 'L';
                break;
            case 'D': side = 'R';
                break;
            case 'S' : side = 'U';
                break;
            case 'A' : side = 'D';
                break;
        }
        return side;
    }

    /**
     * returns side name in spanish
     * used for com moves
     * @param side
     * @return
     */
    public static String convertCharToName( char side){
        String sideName = "";
        switch ( side ){
            case 'L': sideName = "I: Izquierdo";
                break;
            case 'R': sideName = "D: Derecho";
                break;
            case 'U' : sideName = "S: Superior";
                break;
            case 'D' : sideName = "A: Abajo";
                break;
        }
        return sideName;
    }

    private boolean validSide(char side, Box box){
        char [] sidesPerBox = BoxController.getAvailablePositions( box );
        for ( int i = 0; i <sidesPerBox.length ; i++ )
        {
            if ( sidesPerBox[i] == side ){
                return true;
            }
        }
        return false;
    }

    private boolean validPosition( int position){

        int [] boxPositions = this.getBoxesPositions();
        for ( int i = 0; i <boxPositions.length ; i++ )
        {
            if ( boxPositions[i] == position ){
                return true;
            }
        }
        return false;
    }


}
