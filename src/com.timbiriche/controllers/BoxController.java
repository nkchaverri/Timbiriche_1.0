package com.timbiriche.controllers;

import com.timbiriche.models.Box;

/**
 * @author nchaverri
 * @version 1.0
 */
public class BoxController
{
    private Box [][] boxMatrix;

    public BoxController ( Box [][] boxMatrix){
        this.boxMatrix = boxMatrix;
    }

    public Box createBoxModel( int id, int colPos, int rowPos){

        if ( id <= 0 ){
            return null;
        }

        return  new Box( id, colPos,rowPos );
    }

    public int searchUpBox( int row, int col){

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

    public int searchDownBox( int row, int col){

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

    public int searchLeftBox( int row, int col){

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

    public int searchRightBox( int row, int col){

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

    public int searchBox( int row, int col){
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
}
