package com.timbiriche.controllers;

import com.timbiriche.models.Box;
import com.timbiriche.models.Player;

/**
 * @author nchaverri
 * @version 1.0
 */
public class BoxController
{
    private Box [][] boxMatrix;
    private Box[] availableBoxes;
    private char[] availablePositions;

    public BoxController ( Box [][] boxMatrix){
        this.boxMatrix = boxMatrix;
    }

    public Box[][] getBoxMatrix()
    {
        return boxMatrix;
    }

    public Box createBoxModel( int id, int colPos, int rowPos){

        if ( id <= 0 ){
            return null;
        }

        return  new Box( id, colPos,rowPos );
    }

    private int searchUpBox( int row, int col){

        if ( this.invalidPositions( row, col ) ){
            return -1;
        }

        if ( row -1 < 0 ){
            return -1;
        }else{
            row = row -1;
        }

        return this.searchBox( row,col );
    }

    private int searchDownBox( int row, int col){

        if ( this.invalidPositions( row, col ) ){
            return -1;
        }
        if ( row +1 >= this.boxMatrix.length){
            return -1;
        }else{
            row = row +1;
        }

        return this.searchBox( row,col );
    }

    private int searchLeftBox( int row, int col){

        if ( this.invalidPositions( row, col ) ){
            return -1;
        }

        if ( col-1 < 0 ){
            return -1;
        }else{
            col = col-1;
        }

        return this.searchBox( row,col );
    }

    private int searchRightBox( int row, int col){

        if ( this.invalidPositions( row, col ) ){
            return -1;
        }

        if ( col+1 >= this.boxMatrix[0].length){
            return -1;
        }else{
            col = col+1;
        }

        return this.searchBox( row,col );
    }

    private boolean invalidPositions( int row, int col){
        if ( row < 0 || col < 0 ){
            return true;
        }

        if ( row > this.boxMatrix.length -1 || col > this.boxMatrix[0].length-1){
            return true;
        }

        return false;
    }

    private int searchBox( int row, int col){
        int low = 1;
        int high = this.boxMatrix.length * this.boxMatrix[0].length;
        int middle;
        while (low <= high) {
            middle = (low + high) / 2;
            if (this.boxMatrix[row][col].getBoxId() == middle  ) {
                return middle;
            } else if (this.boxMatrix[row][col].getBoxId() > middle) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    public Box searchBoxById( int id ){
        for ( int row = 0; row <this.boxMatrix.length ; row++ )
        {
            for ( int col = 0; col <boxMatrix[row].length ; col++)
            {
                if ( this.boxMatrix[row][col].getBoxId() == id ){
                    return this.boxMatrix[row][col];
                }
            }
        }
        return null;
    }

    public int boxesByPlayer( Player player ){

        int boxesByPlayer = 0;
        for ( int row = 0; row <this.boxMatrix.length ; row++ )
        {
            for ( int col = 0; col <boxMatrix[row].length ; col++)
            {
                if ( this.boxMatrix[row][col].getAssignee()!= null && this.boxMatrix[row][col].getAssignee().getPlayerInitials().equals( player.getPlayerInitials() ) ){
                    boxesByPlayer ++;
                }
            }
        }
        return boxesByPlayer;
    }

    public int getLeftPosition( int row, int col){
        return this.searchLeftBox( row, col );
    }

    public int getRightPosition( int row, int col){
        return this.searchRightBox( row, col );
    }

    public int getUpperPosition( int row, int col){
        return this.searchUpBox( row, col );
    }

    public int getBelowPosition( int row, int col){
        return this.searchDownBox( row, col );
    }

    private void setMarkedValues( Box box){
        int markedPositions = box.getMarkedPositions();

        if ( markedPositions < 4 ){
            markedPositions ++;
        }

        box.setMarkedPositions( markedPositions );
    }

    public boolean allSidesAvailable( Box box){
        return !box.isLeftSide() && !box.isDownSide() && !box.isRightSide() && !box.isUpSide();
    }

    /**
     * method used to mark the letter if the side is available
     * or mark a pipe if the space is not available
     * @param box
     * @return char with the value of the available side
     */
    protected char lefSideAvailable(Box box){
        return !box.isLeftSide() ? 'L':' ';
    }
    protected char rightSideAvailable(Box box){
        return !box.isRightSide() ? 'R':' ';
    }
    protected char upSideAvailable(Box box){
        return !box.isUpSide() ? 'U':' ';
    }
    protected char downSideAvailable(Box box){
        return !box.isDownSide() ? 'D':' ';
    }

    private void setAssignedBox( Box box, Player player ){
        if ( box.getMarkedPositions() == 4 ){
            box.setAssignee( player );
        }
    }

    /**
     * method used to mark left side of an element
     * and the right side if there is an element
     * on the left
     * @param box
     */
    public void markLeftSide( Box box, Player player){
        box.setLeftSide( true );
        this.setMarkedValues( box );
        this.setAssignedBox( box,player );

        System.out.println( "Marcado izquierda en posicion: " + box.getBoxId() );

        if ( searchLeftBox( box.getRowPosition(),box.getColPosition() ) != -1){
            int leftBoxId = searchLeftBox( box.getRowPosition(),box.getColPosition() );
            Box leftBox = this.searchBoxById( leftBoxId );
            leftBox.setRightSide( true );
            this.setMarkedValues( leftBox );
            this.setAssignedBox( leftBox,player );
            System.out.println( "Marcado derecha en posicion: " + leftBox.getBoxId() );
        }
    }
    /**
     * method used to mark right side of an element
     * and the lef side if there is an element
     * on the right
     * @param box
     */
    public void markRightSide( Box box, Player player){
        box.setRightSide( true );
        this.setMarkedValues( box );
        this.setAssignedBox( box,player );

        System.out.println( "Marcado derecha en posicion: " + box.getBoxId() );

        if ( searchRightBox( box.getRowPosition(),box.getColPosition() ) != -1){
            int rightBoxId = searchRightBox( box.getRowPosition(),box.getColPosition() );
            Box rightBox = this.searchBoxById( rightBoxId );
            rightBox.setLeftSide( true );
            this.setMarkedValues( rightBox );
            this.setAssignedBox( rightBox,player );
            System.out.println( "Marcado izquierda en posicion: " + rightBox.getBoxId() );
        }
    }

    public void markUppertSide( Box box, Player player){
        box.setUpSide( true );
        this.setMarkedValues( box );
        this.setAssignedBox( box,player );

        System.out.println( "Marcado arriba en posicion: " + box.getBoxId() );

        if ( searchUpBox( box.getRowPosition(),box.getColPosition() ) != -1){
            int upBoxId = searchUpBox( box.getRowPosition(),box.getColPosition() );
            Box upperBox = this.searchBoxById( upBoxId );
            upperBox.setDownSide( true );
            this.setMarkedValues( upperBox );
            this.setAssignedBox( upperBox,player );
            System.out.println( "Marcado abajo en posicion: " + upperBox.getBoxId() );
        }
    }

    public void markDownSide( Box box, Player player){
        box.setDownSide( true );
        this.setMarkedValues( box );
        this.setAssignedBox( box,player );

        System.out.println( "Marcado abajo en posicion: " + box.getBoxId() );

        if ( searchDownBox( box.getRowPosition(),box.getColPosition() ) != -1){
            int downBoxId = searchDownBox( box.getRowPosition(),box.getColPosition() );
            Box downBox = this.searchBoxById( downBoxId );
            downBox.setUpSide( true );
            this.setMarkedValues( downBox );
            this.setAssignedBox( downBox,player );
            System.out.println( "Marcado arriba en posicion: " + downBox.getBoxId() );

        }
    }

    private int notAssignedBoxes()
    {
        int notAssignedBoxes = 0;
        for ( int row = 0; row < this.boxMatrix.length; row++ )
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


    public Box[] getAvailableBoxes(){

        this.availableBoxes = new Box[this.notAssignedBoxes()];
        int arrayPositions = 0;
        for ( int row = 0; row <this.boxMatrix.length ; row++ )
        {
            for ( int col = 0; col <boxMatrix[row].length ; col++)
            {
                if ( boxMatrix[row][col].getAssignee() !=  null ){
                    continue;
                }else{
                    this.availableBoxes[arrayPositions] = boxMatrix[row][col];
                    arrayPositions++;
                }
            }
        }
        return this.availableBoxes;
    }

    public char[] getAvailablePositions(Box box){
       return this.availablePositions = new char[]{this.lefSideAvailable( box ), this.rightSideAvailable( box ),
                                                    this.upSideAvailable( box ),this.downSideAvailable( box )};

    }
}