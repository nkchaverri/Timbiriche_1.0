package com.timbiriche.models;

public class Box
{
    private int boxId;

    private int colPosition;
    private int rowPosition;

    private boolean upSide;
    private boolean downSide;
    private boolean leftSide;
    private boolean rightSide;

    public Box()
    {
        this.upSide = false;
        this.downSide = false;
        this.leftSide = false;
        this.rightSide = false;
    }

    public Box( int boxId, int colPosition, int rowPosition )
    {
        this();
        this.boxId = boxId;
        this.colPosition = colPosition;
        this.rowPosition = rowPosition;
    }
}
