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
    PREVIEW,
    COLLISION,
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
            case PREVIEW:
                color = Color.YELLOW;
                break;
            case COLLISION:
                color = Color.RED;
                break;
            case UNKNOWNAREA:
            default:
                color = Color.WHITE;
                break;
        }
        return color;
    }
    
    public static FieldStatus getEnumState(String status){
        switch (status){
            case "WATER":
                return WATER;
            case "SHIP":
                return SHIP;
            case "HIT":
                return HIT;
            case "DESTROYED":
                return DESTROYED;
            case "PREVIEW":
                return PREVIEW;
            case "COLLISION":
                return COLLISION;
            default:
                return UNKNOWNAREA;   
        }
    }
}
