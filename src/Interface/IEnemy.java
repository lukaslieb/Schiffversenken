/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Datatypes.FieldStatus;
import Datatypes.PlayerField;

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
     * This function sends win to the enemy if you loose.
     * @param win = true if the enemy wins
     */
    void sendGameWin(boolean win);
    
    /**
     * This function sends messages to the enemy
     * @param msg = String Message
     */
    void comWithEnemy(String msg);
    
    /**
    * This function send wether the reciever is startplayer or not
    * @param firstPlayer = true="reciever is first player", false=reciever does not strat
    */
    void sendFirstPlayer(boolean firstPlayer);
    
    /**
     * 
     * @param x = x-coordinate
     * @param y = y coordinate
     * @param playingfield = value of the field to update
     */
    void UpdateEnemyField(int x, int y, PlayerField playingfield);
}
