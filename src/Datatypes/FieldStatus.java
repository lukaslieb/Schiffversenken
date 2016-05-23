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
    UNKNOWNAREA,
    PLACESHIP,
    UNUSED,
    SHOOT;

    public Color getColor() {
        Color color;
        switch (this) {
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
            case UNUSED:
                color = Color.white;
                break;
            case SHOOT:
                color = Color.green;
            default:
                color = Color.WHITE;

        }
        return color;
    }

    public static FieldStatus getEnumState(String status) {
        switch (status) {
            case "WATER":
                return WATER;
            case "SHIP":
                return SHIP;
            case "HIT":
                return HIT;
            case "DESTROYED":
                return DESTROYED;
            default:
                return UNKNOWNAREA;
        }
    }

    public static String getImg(String state) {
        String s;
        switch (state) {
            case "WATER":
                s="/Gui/water.jpg";
                break;
            case "SHIP":
                s="/Gui/ship.jpg";
                break;
            case "HIT":
                s="/Gui/hit.jpg";
                break;
            case "DESTROYED":
                s="";
                break;
            case "UNUSED":
                s="/Gui/unused.jpg";
                break;
            default:
                s="error";

        }
        return s;
    }
    
    public static String getPic(FieldStatus status){
        String pic;
        switch (status) {
            case WATER:
                pic="/Gui/water.jpg";
                break;
            case SHIP:
                pic="/Gui/ship.jpg";
                break;
            case HIT:
                pic="/Gui/hit.jpg";
                break;
            case DESTROYED:
                pic="/Gui/destroyed.jpg";
                break;
            case UNUSED:
                pic="/Gui/unused.jpg";
                break;
            case PLACESHIP:
                pic="/Gui/placement.jpg";
                break;
            case SHOOT:
                pic="/Gui/shoot.jpg";
            break;
            default:
                pic="/Gui/placement.jpg";

        }
        return pic;
    }
}
