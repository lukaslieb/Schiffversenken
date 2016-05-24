/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.Random;

/**
 *
 * @author Lukas
 */
public class FirstPlayer {
    /**
     * Get the player witch starts the game.
     * @return true if the local player starts
     */
    public static boolean getFirstPlayer(){
        Random r = new Random();
        return r.nextBoolean();
    }
}
