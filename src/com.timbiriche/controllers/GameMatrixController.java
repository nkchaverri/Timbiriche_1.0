package com.timbiriche.controllers;

import com.timbiriche.models.GameMatrix;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * @author nchaveri
 * @version 1.0
 */
public class GameMatrixController
{
    private GameMatrix gameMatrix;
    private BoxController boxController;

    /**
     * method used to create Matrix
     * @param rows
     * @param cols
     * @return
     */
    public GameMatrix createGameMatrix( int rows, int cols){
        if ( rows  <= 0  || cols <= 0){
            return null;
        }

        return this.gameMatrix = new GameMatrix(rows,cols);
    }

    public void completeGameMatrix(){
        int boxId = 1;
        this.boxController = new BoxController();
        for ( int row = 0; row <this.gameMatrix.getMatrix().length ; row++ )
        {
            for ( int col = 0; col <this.gameMatrix.getMatrix()[row].length ; col++, boxId++ )
            {
                this.gameMatrix.getMatrix()[row][col] = this.boxController.createBoxModel( boxId, col, row );
            }
        }
    }

    public void printGameMatrix(){
        for ( int row = 0; row <this.gameMatrix.getMatrix().length ; row++ )
        {
            for ( int col = 0; col <this.gameMatrix.getMatrix()[row].length ; col++)
            {
                System.out.print( "\t\t" + this.gameMatrix.getMatrix()[row][col].getBoxId() + "\t\t " );
            }
            System.out.print( "\n" );
        }

    }


}
