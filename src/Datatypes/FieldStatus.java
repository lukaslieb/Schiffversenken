/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datatypes;

import java.awt.Color;

/**
 *
 * @author Lukas
 */
public enum FieldStatus {
    WATER,
    SHIP,
    HIT,
    DESTROYED,
    ALLREADYHIT,
    UNKNOWNAREA;
    
    public Color getColor(){
        Color color;
        switch (this){
            case WATER:
                color = Color.BLUE;
                break;
            case SHIP:
                color = Color.BLACK;
                break;
            case HIT:
                color = Color.RED;
                break;
            case DESTROYED:
                color = Color.GRAY;
                break;
            default:
                color = Color.WHITE;
                
        }
        return color;
    }
}
