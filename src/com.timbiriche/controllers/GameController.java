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

    public void twoPlayersGame(Player player1, Player player2){

        Player firstPlayer;
        Player secondPlayer;

        int id1 =player1.getPlayerID();
        int id2 =player2.getPlayerID();

        int randomPlayerId = this.playerController.getRandomPlayerId( id1,id2 );
        System.out.println("Random ID: "+ randomPlayerId );

        firstPlayer = player1.getPlayerID() == randomPlayerId ? player1:player2;
        secondPlayer = player1.getPlayerID() != randomPlayerId ? player1:player2;

        System.out.println("First player: "+ firstPlayer.toString() );
        System.out.println("Second player: "+ secondPlayer.toString() );
    }

}
