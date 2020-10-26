package com.timbiriche.models;

/**
 *
 */
public class GameMatrix
{
    private int rows;
    private int cols;

    private Box [][] matrix;

    public GameMatrix( int rows, int cols )
    {
        this.rows = rows;
        this.cols = cols;

        this.matrix = new Box[this.rows][this.cols];
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public Box[][] getMatrix()
    {
        return matrix;
    }


}
