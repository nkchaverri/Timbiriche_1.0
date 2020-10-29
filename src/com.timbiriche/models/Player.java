package com.timbiriche.models;

public class Player
{
    private int playerID;
    private String playerInitials;
    private int wonGames;

    public Player( int playerID, String playerInitials )
    {
        this.playerID = playerID;
        this.playerInitials = playerInitials;
        this.wonGames = 0;
    }

    public int getWonGames()
    {
        return wonGames;
    }

    public void setWonGames( int wonGames )
    {
        this.wonGames = wonGames;
    }

    public String getPlayerInitials()
    {
        return playerInitials;
    }
}
