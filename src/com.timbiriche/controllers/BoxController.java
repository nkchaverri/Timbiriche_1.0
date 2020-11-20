package com.timbiriche.controllers;

import com.timbiriche.models.Box;
import com.timbiriche.models.Player;

import java.util.Arrays;

/**
 * @author nchaverri
 * @version 1.0
 */
public class BoxController
{
    public static Box [][] boxMatrix;
    public static Box[] availableBoxes;
    public static char[] availablePositions;

    public static boolean boxAssigned;

    public static Box nextBox;


    public Box[][] getBoxMatrix()
    {
        return boxMatrix;
    }

    /**
     * recives all the information to create and object model
     * @param id
     * @param colPos
     * @param rowPos
     * @return
     */
    public static Box createBoxModel( int id, int colPos, int rowPos){

        if ( id <= 0 ){
            return null;
        }
        return  new Box( id, colPos,rowPos );
    }

    /**
     * Method used to search if there is any box above
     * the current box selected
     * @param row
     * @param col
     * @return
     */
    public static int searchUpBox( int row, int col){

        if ( invalidPositions( row, col ) ){
            return -1;
        }
        if ( row -1 < 0 ){
            return -1;
        }else{
            row = row -1;
        }

        return searchBox( row,col );
    }


    /**
     * Method used to search if there is any box below
     * the current box selected
     * @param row
     * @param col
     * @return
     */
    public static int searchDownBox( int row, int col){

        if ( invalidPositions( row, col ) ){
            return -1;
        }
        if ( row +1 >= boxMatrix.length){
            return -1;
        }else{
            row = row +1;
        }

        return searchBox( row,col );
    }

    /**
     * Method used to search if there is any box to the left
     * the current box selected
     * @param row
     * @param col
     * @return
     */
    public static int searchLeftBox( int row, int col){

        if ( invalidPositions( row, col ) ){
            return -1;
        }

        if ( col-1 < 0 ){
            return -1;
        }else{
            col = col-1;
        }

        return searchBox( row,col );
    }

    /**
     * Method used to search if there is any box to the right
     * the current box selected
     * @param row
     * @param col
     * @return
     */
    public static int searchRightBox( int row, int col){

        if ( invalidPositions( row, col ) ){
            return -1;
        }

        if ( col+1 >= boxMatrix[0].length){
            return -1;
        }else{
            col = col+1;
        }

        return searchBox( row,col );
    }

    public static boolean invalidPositions( int row, int col){
        if ( row < 0 || col < 0 ){
            return true;
        }

        if ( row > boxMatrix.length -1 || col > boxMatrix[0].length-1){
            return true;
        }

        return false;
    }

    /**
     * Search boxes using its position and based in the binary
     * search algorithm
     * @param row
     * @param col
     * @return
     */
    public static int searchBox( int row, int col){
        int low = 1;
        int high = boxMatrix.length * boxMatrix[0].length;
        int middle;
        while (low <= high) {
            middle = (low + high) / 2;
            if (boxMatrix[row][col].getBoxId() == middle  ) {
                return middle;
            } else if (boxMatrix[row][col].getBoxId() > middle) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    /**
     * returns the box object linked to the id provided
     * @param id
     * @return
     */
    public static Box searchBoxById( int id ){
        for ( int row = 0; row <boxMatrix.length ; row++ )
        {
            for ( int col = 0; col <boxMatrix[row].length ; col++)
            {
                if ( boxMatrix[row][col].getBoxId() == id ){
                    return boxMatrix[row][col];
                }
            }
        }
        return null;
    }

    /**
     * return how many boxes has one player
     * assigned
     * @param player
     * @return
     */
    public static int boxesByPlayer( Player player ){

        int boxesByPlayer = 0;
        for ( int row = 0; row <boxMatrix.length ; row++ )
        {
            for ( int col = 0; col <boxMatrix[row].length ; col++)
            {
                if ( boxMatrix[row][col].getAssignee()!= null && boxMatrix[row][col].getAssignee().getPlayerInitials().equals( player.getPlayerInitials() ) ){
                    boxesByPlayer ++;
                }
            }
        }
        return boxesByPlayer;
    }

    /**
     * verifies if a particular box has any position left
     * and increases the count
     * @param box
     */
    private static void setMarkedValues( Box box){
        int markedPositions = box.getMarkedPositions();

        if ( markedPositions < 4 ){
            markedPositions ++;
        }

        box.setMarkedPositions( markedPositions );
    }


    /**
     * method used to mark the letter if the side is available
     * or mark a pipe if the space is not available
     * @param box
     * @return char with the value of the available side
     */
    public static char lefSideAvailable(Box box){
        return !box.isLeftSide() ? 'L':' ';
    }
    public static char rightSideAvailable(Box box){
        return !box.isRightSide() ? 'R':' ';
    }
    public static char upSideAvailable(Box box){
        return !box.isUpSide() ? 'U':' ';
    }
    public static char downSideAvailable(Box box){
        return !box.isDownSide() ? 'D':' ';
    }

    private static void setAssignedBox( Box box, Player player ){
        if ( box.getMarkedPositions() == 4 ){
            box.setAssignee( player );
        }
    }

    private static void setNextBox(Box box){
        nextBox = box;
    }

    /**
     * method used to mark left side of an element
     * and the right side if there is an element
     * on the left
     * @param box
     */
    public static void markLeftSide( Box box, Player player){
        box.setLeftSide( true );
        setMarkedValues( box );
        setAssignedBox( box,player );

//        System.out.println( "Marcado izquierda en posicion: " + box.getBoxId() );
        Box leftBox = null;
        if ( searchLeftBox( box.getRowPosition(),box.getColPosition() ) != -1){
            int leftBoxId = searchLeftBox( box.getRowPosition(),box.getColPosition() );
            leftBox = searchBoxById( leftBoxId );
            leftBox.setRightSide( true );
            setMarkedValues( leftBox );
            setAssignedBox( leftBox,player );
//            System.out.println( "Marcado derecha en posicion: " + leftBox.getBoxId() );
        }

        setNextBox(leftBox);
    }
    /**
     * methods used to mark the corresponding side of an element
     * and any object next to it if there is any
     * @param box
     */
    public static void markRightSide( Box box, Player player){
        box.setRightSide( true );
        setMarkedValues( box );
        setAssignedBox( box,player );

//        System.out.println( "Marcado derecha en posicion: " + box.getBoxId() );
        Box rightBox= null;
        if ( searchRightBox( box.getRowPosition(),box.getColPosition() ) != -1){
            int rightBoxId = searchRightBox( box.getRowPosition(),box.getColPosition() );
            rightBox = searchBoxById( rightBoxId );
            rightBox.setLeftSide( true );
            setMarkedValues( rightBox );
            setAssignedBox( rightBox,player );
//            System.out.println( "Marcado izquierda en posicion: " + rightBox.getBoxId() );
        }

        setNextBox(rightBox);
    }

    public static void markUppertSide( Box box, Player player){
        box.setUpSide( true );
        setMarkedValues( box );
        setAssignedBox( box,player );

//        System.out.println( "Marcado arriba en posicion: " + box.getBoxId() );
        Box upperBox = null;
        if ( searchUpBox( box.getRowPosition(),box.getColPosition() ) != -1){
            int upBoxId = searchUpBox( box.getRowPosition(),box.getColPosition() );
            upperBox = searchBoxById( upBoxId );
            upperBox.setDownSide( true );
            setMarkedValues( upperBox );
            setAssignedBox( upperBox,player );
//            System.out.println( "Marcado abajo en posicion: " + upperBox.getBoxId() );
        }
        setNextBox(upperBox);
    }

    public static void markDownSide( Box box, Player player){
        box.setDownSide( true );
        setMarkedValues( box );
        setAssignedBox( box,player );

//        System.out.println( "Marcado abajo en posicion: " + box.getBoxId() );
        Box downBox = null;
        if ( searchDownBox( box.getRowPosition(),box.getColPosition() ) != -1){
            int downBoxId = searchDownBox( box.getRowPosition(),box.getColPosition() );
            downBox = searchBoxById( downBoxId );
            downBox.setUpSide( true );
            setMarkedValues( downBox );
            setAssignedBox( downBox,player );
//            System.out.println( "Marcado arriba en posicion: " + downBox.getBoxId() );
            setNextBox(downBox);
        }
    }

    /**
     * count for not assigned boxes
     * @return
     */
    private static int notAssignedBoxes()
    {
        int notAssignedBoxes = 0;
        for ( int row = 0; row < boxMatrix.length; row++ )
        {
            for ( int col = 0; col < boxMatrix[ row ].length; col++ )
            {
                if ( boxMatrix[ row ][ col ].getAssignee() == null )
                {
                    notAssignedBoxes++;
                }
            }
        }
        return notAssignedBoxes;
    }


    public static Box[] getAvailableBoxes(){

        availableBoxes = new Box[notAssignedBoxes()];
        int arrayPositions = 0;
        for ( int row = 0; row <boxMatrix.length ; row++ )
        {
            for ( int col = 0; col <boxMatrix[row].length ; col++)
            {
                if ( boxMatrix[row][col].getAssignee() !=  null ){
                    continue;
                }else{
                    availableBoxes[arrayPositions] = boxMatrix[row][col];
                    arrayPositions++;
                }
            }
        }
        return availableBoxes;
    }

    public static Box[] getBoxThreeSides(){

        int posibleMoves = 0;
        for ( int row = 0; row <boxMatrix.length ; row++ )
        {
            for ( int col = 0; col <boxMatrix[row].length ; col++)
            {
                if ( boxMatrix[row][col].getMarkedPositions() <3 || boxMatrix[row][col].getAssignee() != null  ){
                    continue;
                }else{
                    posibleMoves++;
                }
            }
        }

        Box[] boxesThreeMoves = new Box[posibleMoves];
        int positions = 0;
        for ( int row = 0; row <boxMatrix.length ; row++ )
        {
            for ( int col = 0; col <boxMatrix[row].length ; col++)
            {
                if ( boxMatrix[row][col].getMarkedPositions() == 3 ){
                    boxesThreeMoves[positions] = boxMatrix[row][col];
                    positions++;
                }
            }
        }
        return boxesThreeMoves;
    }

    public static char getLastSideAvailable(Box box){
        char side = ' ';
        char[] sidesAvailable = getAvailablePositions( box );
        for ( int i = 0; i <sidesAvailable.length ; i++ )
        {
          if ( sidesAvailable[i] != ' ' ){
              side = sidesAvailable[i];
          }
        }
        return side;
    }

    public static char[] getAvailablePositions(Box box){
       return availablePositions = new char[]{lefSideAvailable( box ), rightSideAvailable( box ),
                                                    upSideAvailable( box ),downSideAvailable( box )};

    }
}