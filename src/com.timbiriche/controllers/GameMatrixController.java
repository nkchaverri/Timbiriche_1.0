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
                String stringBox= "";
                stringBox += System.lineSeparator();
                stringBox += "\t" + boxController.upSideAvailable( this.gameMatrix.getMatrix()[row][col] ) +"\t";
                stringBox += System.lineSeparator();
                stringBox += boxController.lefSideAvailable( this.gameMatrix.getMatrix()[row][col] );
                if ( this.gameMatrix.getMatrix()[row][col].getAssignee()!= null ){
                    stringBox += "\t" + this.gameMatrix.getMatrix()[row][col].getAssignee().getPlayerInitials() + "\t ";
                }else {
                    stringBox += "\t" + this.gameMatrix.getMatrix()[row][col].getBoxId() + "\t ";
                }
                stringBox += boxController.rightSideAvailable( this.gameMatrix.getMatrix()[row][col] ) + System.lineSeparator();
                stringBox += "\t" +boxController.downSideAvailable( this.gameMatrix.getMatrix()[row][col] ) + "\t";
                stringBox += System.lineSeparator();
                System.out.print( stringBox );
            }
        }
        System.lineSeparator();
    }

}
