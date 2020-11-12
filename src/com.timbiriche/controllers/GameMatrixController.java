package com.timbiriche.controllers;

import com.timbiriche.models.Box;
import com.timbiriche.models.GameMatrix;

/**
 * @author nchaveri
 * @version 1.0
 */
public class GameMatrixController
{
    private GameMatrix gameMatrix;
    private int rows;
    private int cols;


    private static final char BOX_DOT = 0x00B7;
    private static final char BOX_HORIZONTAL_LINE_CHAR = 0x2500;
    private static final char BOX_VERTICAL_LINE = 0x2502;
    private static final String BOX_EMPTY_SPACE = " ";
    private static final String JUMP_TO_NEXT_LINE = "\n";
    private static final int BOX_WIDTH = 5;


    public GameMatrixController( int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;

        this.gameMatrix = new GameMatrix( this.rows, this.cols );
    }

    public GameMatrix getGameMatrix(){
        return this.gameMatrix;
    }

    public void completeGameMatrix(){
        int boxId = 1;
        for ( int row = 0; row <this.gameMatrix.getMatrix().length ; row++ )
        {
            for ( int col = 0; col <this.gameMatrix.getMatrix()[row].length ; col++, boxId++ )
            {
                this.gameMatrix.getMatrix()[row][col] = BoxController.createBoxModel( boxId, col, row );
            }
        }
    }


    public void printMatrix( Box[][] matrix) {
        String matrixInConsole = "";
        for (int rowIndex = 0, defaultContentForAllBoxes = 1; rowIndex < matrix.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrix[rowIndex].length; columnIndex++) {
                if (rowIndex == 0) { // only print top line and top left dot for first row
                    matrixInConsole += BOX_DOT;
                    matrixInConsole += printHorizontalLine(matrix[rowIndex][columnIndex].isUpSide());
                    if (columnIndex == matrix[rowIndex].length-1) { // upper right dot for first row
                        matrixInConsole += BOX_DOT;
                    }
                }
            }
            if (rowIndex == 0) {
                matrixInConsole += JUMP_TO_NEXT_LINE;
            }
            for (int columnIndex = 0; columnIndex < matrix[rowIndex].length; columnIndex++, defaultContentForAllBoxes++) {
                if (columnIndex == 0) { // only print left line for first column
                    matrixInConsole += printVerticalLine(matrix[rowIndex][columnIndex].isLeftSide());
                }
                matrixInConsole += generateBoxContent(matrix[rowIndex][columnIndex], defaultContentForAllBoxes);
                matrixInConsole += printVerticalLine(matrix[rowIndex][columnIndex].isRightSide()); // always print right line for all boxes
            }
            matrixInConsole += JUMP_TO_NEXT_LINE;
            for (int columnIndex = 0; columnIndex < matrix[rowIndex].length; columnIndex++) {
                if (columnIndex == 0) { // only print top left dot for first column
                    matrixInConsole += BOX_DOT;
                }
                matrixInConsole += printHorizontalLine(matrix[rowIndex][columnIndex].isDownSide()); // always print bottom line for all boxes
                matrixInConsole += BOX_DOT; // always print bottom right dot for all boxes
            }
            matrixInConsole += JUMP_TO_NEXT_LINE;
        }
        System.out.println(matrixInConsole);
    }

    private static String generateBoxContent(Box box, int content) {
        String boxContent = BOX_EMPTY_SPACE;
        String boxOwner = "";

        if (box.getAssignee() != null) {
            boxOwner = box.getAssignee().getPlayerInitials();
        } else {
            boxOwner = String.valueOf(content);
        }

        if (boxOwner.length() <= 1) { // if player initials or default number is only one character/digit, fill it up with two blank spaces
            boxContent += BOX_EMPTY_SPACE + boxOwner + BOX_EMPTY_SPACE;
        } else if (boxOwner.length() <= 2) { // if player initials or default number is only two characters/digits, fill it up with one blank space
            boxContent += BOX_EMPTY_SPACE + boxOwner;
        } else {
            boxContent += boxOwner;
        }

        boxContent += BOX_EMPTY_SPACE;

        return boxContent;
    }

    private static String printHorizontalLine(boolean isMarked) {
        String horizontalLine = BOX_EMPTY_SPACE;
        for (int i = 0; i < BOX_WIDTH-2; i++) { // remove two from BOX_WIDTH for blank space to the left and the right
            horizontalLine += isMarked ? BOX_HORIZONTAL_LINE_CHAR : BOX_EMPTY_SPACE;
        }
        horizontalLine += BOX_EMPTY_SPACE;
        return horizontalLine;
    }

    private static String printVerticalLine(boolean isMarked) {
        return isMarked ? String.valueOf(BOX_VERTICAL_LINE) : BOX_EMPTY_SPACE;
    }

}
