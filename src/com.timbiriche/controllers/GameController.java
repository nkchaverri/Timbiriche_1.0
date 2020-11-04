package com.timbiriche.controllers;

import com.timbiriche.models.Player;

import java.io.IOException;

public class GameController
{
    private GameMatrixController gameMatrixController;
    private BoxController boxController;
    private PlayerController playerController;
    private Player player1;
    private Player player2;

    public boolean validRowsAndCols( int rows, int cols){
        if ( (rows  <= 2  || cols <= 2) || (rows > 10 || cols > 10)){
            return false;
        }

        return true;
    }

    public void intilizePlayers(){
        this.playerController = new PlayerController();
    }

    public String showAvailablePlayers(){
       return this.playerController.getAvailabePlayersList();
    }



    public void initAndFillGameBoard(int rows, int cols){

        this.gameMatrixController = new GameMatrixController( rows, cols );
        this.boxController = new BoxController( gameMatrixController.getGameMatrix().getMatrix() );


        this.gameMatrixController.completeGameMatrix( this.boxController );
    }

    public void printMatrix(){
        this.gameMatrixController.printMatrix( this.boxController.getBoxMatrix() );
    }

}
