package com.timbiriche.controllers;

import com.timbiriche.models.Box;

/**
 *
 */
public class BoxController
{
    public Box createBoxModel( int id, int colPos, int rowPos){

        if ( id <= 0 ){
            return null;
        }

        return  new Box( id, colPos,rowPos );
    }
}
