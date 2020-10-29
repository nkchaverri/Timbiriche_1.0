package com.timbiriche.controllers;

import com.timbiriche.models.Player;

public class PlayerController
{

    public Player newPlayerCreated( int id, String playerInitials){
        Player newPlayer;

        int intLength = String.valueOf(id).length();

        if ( intLength < 4 || intLength > 8 ){
            return null;
        }else {
            newPlayer = new Player( id,playerInitials );
        }
        return newPlayer;
    }
}
