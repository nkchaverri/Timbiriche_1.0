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

    private int markedPositions;
    private boolean filledBox;

    private Player assignee;
    private char lastSideAssigned;

    private Box()
    {
        this.upSide = false;
        this.downSide = false;
        this.leftSide = false;
        this.rightSide = false;
        this.markedPositions = 0;
        this.filledBox = false;
    }

    public Box( int boxId, int colPosition, int rowPosition )
    {
        this();
        this.boxId = boxId;
        this.colPosition = colPosition;
        this.rowPosition = rowPosition;
    }

    public int getBoxId()
    {
        return boxId;
    }

    public void setBoxId( int boxId )
    {
        this.boxId = boxId;
    }

    public int getColPosition()
    {
        return colPosition;
    }

    public void setColPosition( int colPosition )
    {
        this.colPosition = colPosition;
    }

    public int getRowPosition()
    {
        return rowPosition;
    }

    public void setRowPosition( int rowPosition )
    {
        this.rowPosition = rowPosition;
    }

    public boolean isUpSide()
    {
        return upSide;
    }

    public void setUpSide( boolean upSide )
    {
        this.upSide = upSide;
    }

    public boolean isDownSide()
    {
        return downSide;
    }

    public void setDownSide( boolean downSide )
    {
        this.downSide = downSide;
    }

    public boolean isLeftSide()
    {
        return leftSide;
    }

    public void setLeftSide( boolean leftSide )
    {
        this.leftSide = leftSide;
    }

    public boolean isRightSide()
    {
        return rightSide;
    }

    public void setRightSide( boolean rightSide )
    {
        this.rightSide = rightSide;
    }

    public int getMarkedPositions()
    {
        return markedPositions;
    }

    public void setMarkedPositions( int markedPositions )
    {
        this.markedPositions = markedPositions;
    }

    public boolean isFilledBox()
    {
        return filledBox;
    }

    public void setFilledBox( boolean filledBox )
    {
        this.filledBox = filledBox;
    }

    public Player getAssignee()
    {
        return assignee;
    }

    public void setAssignee( Player assignee )
    {
        this.assignee = assignee;
    }

    public char getLastSideAssigned()
    {
        return lastSideAssigned;
    }

    public void setLastSideAssigned( char lastSideAssigned )
    {
        this.lastSideAssigned = lastSideAssigned;
    }

    public String toString(){
        return "Box id: " + this.boxId + ", Assignee: " + this.assignee;
    }
}
