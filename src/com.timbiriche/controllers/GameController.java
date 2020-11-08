package com.timbiriche.controllers;

import com.timbiriche.models.Player;

import java.io.IOException;

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

}
