package com.timbiriche.controllers;

import com.timbiriche.models.Player;

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
        playerController = new PlayerController();
    }
    public void intilizeMatrix(int rows, int cols){

        this.gameMatrixController = new GameMatrixController( rows, cols );
        this.boxController = new BoxController( gameMatrixController.getGameMatrix().getMatrix() );
    }
}
