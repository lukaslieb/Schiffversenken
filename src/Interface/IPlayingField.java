/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Datatypes.FieldStatus;
import Datatypes.PlayerField;

/**
 * This interface define the methods how communicate with the Spielfeld Gui.
 * @author Lukas
 */
public interface IPlayingField {
    /**
     * Method for updating the actual Field. 
     * @param x = x Coordinate of Field
     * @param y = y Coordinate of Field
     * @param field = ENEMY or OWN
     * @param status = status of Field (enum FieldStatus)
     */
    void updateField(int x, int y, PlayerField field, FieldStatus status); 
    
    /**
     * This function tells the Playfield if the local player has won.
     * @param won -> true = won, false = lose
     */
    void gameOver(boolean won);
    
    /**
     * This function changes the title image to show how can shoot
     * @param myTurn -> true = myTurn, false = enemyTurn
     */
    void myTurn(boolean myTurn);
}
