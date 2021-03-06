package com.timbiriche.controllers;
import com.timbiriche.Utils.FileUtils;
import com.timbiriche.models.Player;
import java.util.Random;

/**
 * Player controller, includes all methods that handle
 * players arrays and models creations
 * @author nchaverri
 */

public class PlayerController
{
    private Player[] players;
    private Player[] availablePlayers;
    private FileUtils fileUtils;

    public PlayerController()
    {
        this.players  = new Player[100];
        this.fileUtils = new FileUtils();
        //initialize available players
        initializePlayers();
    }

    /**
     * This method create players file to persist
     * any players created
     */
    public void createPlayerFile(){
        fileUtils.createFile( this.availablePlayers );
    }

    /**
     * Receives two integers and gets one randomly
     * to get the first player to move
     * @param id1
     * @param id2
     * @return
     */
    public int getRandomPlayerId( int id1, int id2){

        int randomOfTwoInts = new Random().nextBoolean() ? id1 : id2;
        return randomOfTwoInts;
    }

    /**
     * Retrieve player object by its id
     * @param id
     * @return
     */
    public Player getPlayerById(int id){
        for ( int i = 0; i <this.availablePlayers.length ; i++ )
        {
            if ( this.availablePlayers[i].getPlayerID() == id ){
                return this.availablePlayers[i];
            }
        }
        return null;
    }

    /**
     * search and return player created as
     * computer
     * @return
     */
    public Player getComputerPlayer(){
        for ( int i = 0; i <this.players.length ; i++ )
        {
            if ( this.players[i].isComputer()){
                return this.players[i];
            }
        }
        return null;
    }

    /**
     * initialize players by reading anything already included
     * in the file "Players.txt"
     */
    private void initializePlayers(){
      this.fileUtils.importPlayers( this.players );
      this.getAvailablePlayers();
    }

    /**
     * Evaluate if there is a player with that id
     * @param id
     * @return
     */
    public boolean playerExist( int id ){
        for ( int i = 0; i <this.availablePlayers.length ; i++ )
        {
            if ( this.availablePlayers[i].getPlayerID() == id ){
                return true;
            }
        }
        return false;
    }

    /**
     * evaluates if there is an existing player
     * by its initials
     * @param initials
     * @return
     */
    public boolean playerInitialsExist( String initials ){
        for ( int i = 0; i <this.availablePlayers.length ; i++ )
        {
            if ( this.availablePlayers[i].getPlayerInitials().equals( initials ) ){
                return true;
            }
        }
        return false;
    }

    /**
     * validates if id length between valid length range
     * @param id
     * @return
     */
    public boolean isValidId( int id ){
        int intLength = String.valueOf(id).length();

        if ( intLength < 4 || intLength > 8 ){
            return false;
        }
        return true;
    }

    public Player createNewPlayer( int id, String playerInitials){
        Player newPlayer;
        newPlayer = new Player( id,playerInitials );
        this.addPlayerToList( newPlayer );
        this.getAvailablePlayers();
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

    /**
     * count for all the players created
     * @return
     */
    private int playersCreated(){
        int count = 0;
        for ( int i = 0; i <this.players.length ; i++ )
        {
            if ( this.players[i] != null && !this.players[i].isComputer() )
            {
                count++;
            }
        }
        return count;
    }

    /**
     * Receives a player array and uses bubble sort logic
     * to order them descendenly sort by its points
     * @param arrayPlayers
     * @return
     */
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

    /**
     * returns an array of available players objects
     * @return
     */
    public Player[] getAvailablePlayers(){

        this.availablePlayers = new Player[this.playersCreated()];
        int position = 0;
        for ( int i = 0; i <this.players.length ; i++ )
        {
            if ( this.players[i] != null && !this.players[i].isComputer() )
            {
                this.availablePlayers[position] = this.players[i];
                position ++;
            }
        }
        return this.availablePlayers ;
    }

    /**
     * returns all the available players previosuly
     * order by its points in a string
     * @return
     */
    public String getAvailabePlayersList(){

        return this.getPlayerList( this.orderDescendenly( this.availablePlayers )) ;
    }

    private String getPlayerList( Player[] playersToPrint){
        String result="";
        for ( int i = 0; i <playersToPrint.length ; i++ )
        {
            if ( !playersToPrint[i].isComputer() ){
                result += " Id Jugador: " + playersToPrint[i].getPlayerID() +
                        ", Iniciales: " + playersToPrint[i].getPlayerInitials() +
                        ", Total de puntos: " + playersToPrint[i].getPoints() +
                        ", Juegos Ganados : " + playersToPrint[i].getWonGames()+" \n";
            }
        }
        return result;
    }

    /**
     * returns all the second players available
     * excluding the first player  created /picked
     * @param player
     * @return
     */
    public String getAvailableSecondPlayers(Player player){
        int position = 0;
        int size = this.availablePlayers.length;
        Player [] secondPlayer = new Player[size-1];
        for ( int i = 0; i <this.availablePlayers.length ; i++ )
        {
            if ( this.availablePlayers[i] != player ){
                secondPlayer[position] = this.availablePlayers[i];
                position ++;
            }
        }
        return this.getPlayerList( this.orderDescendenly( secondPlayer )) ;
    }
}
