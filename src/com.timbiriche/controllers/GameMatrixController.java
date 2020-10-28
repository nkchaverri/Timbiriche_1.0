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

    public GameMatrix getGameMatrix(){
        return this.gameMatrix;
    }

    public void completeGameMatrix( BoxController boxController){
        int boxId = 1;
        for ( int row = 0; row <this.gameMatrix.getMatrix().length ; row++ )
        {
            for ( int col = 0; col <this.gameMatrix.getMatrix()[row].length ; col++, boxId++ )
            {
                this.gameMatrix.getMatrix()[row][col] = boxController.createBoxModel( boxId, col, row );
            }
        }
    }
    public void printGameMatrix( BoxController boxController ){
        for ( int row = 0; row <this.gameMatrix.getMatrix().length ; row++ )
        {
            for ( int col = 0; col <this.gameMatrix.getMatrix()[row].length ; col++)
            {
                System.out.print( "\n" );
                if ( boxController.upSideAvailable( this.gameMatrix.getMatrix()[row][col] ) != ' '){
                    System.out.println( "\t" +boxController.upSideAvailable( this.gameMatrix.getMatrix()[row][col] ) + "\t" );
                }
                if ( boxController.lefSideAvailable( this.gameMatrix.getMatrix()[row][col] ) != ' '){
                    System.out.print( boxController.lefSideAvailable( this.gameMatrix.getMatrix()[row][col] ));
                }
                System.out.print( "\t" + this.gameMatrix.getMatrix()[row][col].getBoxId() + "\t " );

                if ( boxController.rightSideAvailable( this.gameMatrix.getMatrix()[row][col] ) != ' '){
                    System.out.print( boxController.rightSideAvailable( this.gameMatrix.getMatrix()[row][col] ));
                }

                System.out.print( "\n" );

                if ( boxController.downSideAvailable( this.gameMatrix.getMatrix()[row][col] ) != ' '){
                    System.out.println( "\t" +boxController.downSideAvailable( this.gameMatrix.getMatrix()[row][col] ) + "\t" );
                }
            }
            System.out.print( "---------" );
        }

    }

}
