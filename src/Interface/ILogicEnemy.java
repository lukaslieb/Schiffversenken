/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Datatypes.FieldStatus;

/**
 * This interface define the methods how the enemey can communicate with the local logic.
 * @author Lukas
 */
public interface ILogicEnemy {
    /**
     * recive a shoot from an enemy (network player or ki).
     * @param x = x coordinate
     * @param y = y coordinate
     * @return FieldStatus for the status of the Field witch was shoot
     */
    FieldStatus shootFromEnemy(int x, int y);
    
    /**
     * This method takes the fieldstatus from the enemy.
     * @param x
     * @param y
     * @param FieldStatus status
     */
    void shootReply(int x, int y, FieldStatus status);
    
    void gameWin(boolean win);
    
    void UpdateField(int x, int y, FieldStatus status);
    
    /**
    * This function send wether the reciever is startplayer or not
    * @param firstPlayer = true="reciever is first player", false=reciever does not strat
    */
    void setFirstPlayer(boolean firstPlayer);
    
    void setReady();
}
