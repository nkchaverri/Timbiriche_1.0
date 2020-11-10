package com.timbiriche.controllers;

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
        this.boxController = new BoxController( gameMatrixController.getGameMatrix().getMatrix() );
        this.gameMatrixController.completeGameMatrix( this.boxController );
    }

    public BoxController getBoxController()
    {
        return boxController;
    }

    public void printMatrix(){
        this.gameMatrixController.printMatrix( this.boxController.getBoxMatrix() );
    }

    public String showAvailableSides( Box box ){
        char[] positions= this.boxController.getAvailablePositions( box );
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

    public String availablePositionsList(){
        Box[] boxesAvailable = this.boxController.getAvailableBoxes();
        String result = "Posiciones Disponibles: \n" ;
        for ( int i = 0; i <boxesAvailable.length ; i++ )
        {
            result+= boxesAvailable[i].getBoxId() + "\t";
        }
        return result;
    }

    public int[] getBoxesPositions(){

        Box[] boxArray = this.boxController.getAvailableBoxes();
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
        return this.boxController.getAvailableBoxes().length>0;
    }

    public boolean createMove( Player currentPlayer,Box box, int position, char side){
        boolean madeMove = false;
        if ( !validSide( side,box )|| !validPosition( position ) ){
            return madeMove;
        }

        markMove( side, box,currentPlayer );
        return true;
    }

    private void markMove(char side, Box box, Player player){
        side = convertChar( side );
        switch ( side ){
            case 'L': this.getBoxController().markLeftSide( box,player );
            break;
            case 'R': this.getBoxController().markRightSide( box,player );
                break;
            case 'U': this.getBoxController().markUppertSide( box,player );
                break;
            case 'D': this.getBoxController().markDownSide( box,player );
                break;
        }
    }

    private char convertChar( char side){
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

    private boolean validSide(char side, Box box){
        side = this.convertChar( side );
        char [] sidesPerBox = this.getBoxController().getAvailablePositions( box );
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
