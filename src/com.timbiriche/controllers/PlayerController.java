package com.timbiriche.controllers;

import com.timbiriche.models.Player;

public class PlayerController
{
    private Player [] players;

    public PlayerController( )
    {
        this.players  = new Player[100];
    }

    public Player newPlayerCreated( int id, String playerInitials){
        Player newPlayer;

        int intLength = String.valueOf(id).length();

        if ( intLength < 4 || intLength > 8 ){
            return null;
        }

        newPlayer = new Player( id,playerInitials );
        this.addPlayerToList( newPlayer );
        return newPlayer;
    }

    public void addPlayerToList( Player player){
        for ( int i = 0; i <this.players.length ; i++ )
        {
            if ( this.players[i] == null ){
                this.players[i]= player;
                return;
            }
        }
    }

    private int playersCreated(){
        int count = 0;
        for ( int i = 0; i <this.players.length ; i++ )
        {
            if ( this.players[i] != null )
            {
                count++;
            }
        }
        return count;
    }

    private Player[] orderDescendenly( Player[] arrayPlayers) {
        for (int i = 0; i < arrayPlayers.length; i++) {
            for (int j = 0; j < arrayPlayers.length - 1; j++) {
                if (arrayPlayers[j].getPoints() < arrayPlayers[j + 1].getPoints()) {
                    Player aux = arrayPlayers[j];
                    arrayPlayers[j] = arrayPlayers[j + 1];
                    arrayPlayers[j + 1] = aux;
                }
            }
        }
        return arrayPlayers;
    }

    public String getPlayers(){

        Player [] availablePlayers = new Player[this.playersCreated()];
        for ( int i = 0; i <this.players.length ; i++ )
        {
            if ( this.players[i] != null )
            {
                availablePlayers[i] = this.players[i];
            }
        }
        return this.getPlayerList( this.orderDescendenly( availablePlayers )) ;
    }

    private String getPlayerList( Player[] playersToPrint){
        String result="";
        for ( int i = 0; i <playersToPrint.length ; i++ )
        {
            result += " Player ID: " + playersToPrint[i].getPlayerID() + ", Initials: "
                                        + playersToPrint[i].getPlayerInitials() + ", Total of Points: "
                                            + playersToPrint[i].getPoints() + " \n";
        }
        return result;
    }


}
