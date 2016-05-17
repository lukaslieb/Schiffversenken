/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Datatypes.FieldStatus;

/**
 * This interface define the methods how to communicate with the enemy (computer or network player).
 * @author Lukas
 */
public interface IEnemy {
    /**
     * This function sends the last move from the local player to the enemy.
     * @param x = x coordinate
     * @param y = y coordinate
     */
    void sendMoveToEnemy(int x, int y);
    
    /**
     * This function sends messages to the enemy
     * @param msg = String Message
     */
    void comWithEnemy(String msg);
}
