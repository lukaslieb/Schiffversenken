/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datatypes;

/**
 *
 * @author Lukas
 */
public enum ShipAlignment {
    HORIZONTAL,
    VERTICAL;
    
    public static ShipAlignment invertAlignment(ShipAlignment SA){
        if(SA == ShipAlignment.HORIZONTAL){
            return ShipAlignment.VERTICAL;
        }
        else{
            return ShipAlignment.HORIZONTAL;
        }
    }
}

