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
    COLLISION,
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
            case "PREVIEW":
                return PLACESHIP;
            case "COLLISION":
                return COLLISION;
            default:
                return UNKNOWNAREA;
        }
    }

    public static String getImg(String state) {
        String s;
        switch (state) {
            case "WATER":
                s="/Gui/Picture/water.jpg";
                break;
            case "SHIP":
                s="/Gui/Picture/ship.jpg";
                break;
            case "HIT":
                s="/Gui/Picture/hit.jpg";
                break;
            case "DESTROYED":
                s="/Gui/Picture/destroyed.jpg";
                break;
            case "UNUSED":
                s="/Gui/Picture/unused.jpg";
                break;
            default:
                s="/Gui/Picture/unused.jpg";

        }
        return s;
    }
    
    public static String getPic(FieldStatus status){
        String pic;
        switch (status) {
            case WATER:
                pic="/Gui/Picture/water.jpg";
                break;
            case SHIP:
                pic="/Gui/Picture/ship.jpg";
                break;
            case HIT:
                pic="/Gui/Picture/hit.jpg";
                break;
            case DESTROYED:
                pic="/Gui/Picture/destroyed.jpg";
                break;
            case UNUSED:
                pic="/Gui/Picture/unused.jpg";
                break;
            case PLACESHIP:
                pic="/Gui/Picture/placement.jpg";
                break;
            case SHOOT:
                pic="/Gui/Picture/shoot.jpg";
            case COLLISION:
                pic="/Gui/Picture/destroyed.jpg";
            break;
            default:
                pic="/Gui/Picture/placement.jpg";

        }
        return pic;
    }
}
