package com.timbiriche.models;
import java.io.Serializable;

public class Player implements Serializable
{
    private int playerID;
    private String playerInitials;
    private int wonGames;
    private double points;

    public Player( int playerID, String playerInitials )
    {
        this.playerID = playerID;
        this.playerInitials = playerInitials;
        this.wonGames = 0;
        this.points = 0.0;
    }

    public int getPlayerID()
    {
        return playerID;
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

    public double getPoints()
    {
        return points;
    }

    public void setPoints( double points )
    {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Id:" + this.playerID + ", Initials: " + this.playerInitials + ", Points: " + this.points
                + ", Games: " + this.wonGames;
    }
}
