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
public enum PlayerField {
    ENEMY,
    OWN;
    
    public static PlayerField getEnumState(String playingfield) {
        switch (playingfield) {
            case "ENEMY":
                return ENEMY;
            case "OWN":
                return OWN;
        }
    }
}
