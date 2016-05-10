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
}
