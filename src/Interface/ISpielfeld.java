/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Datatypes.FieldStatus;

/**
 * This interface define the methods how communicate with the Spielfeld Gui.
 * @author Lukas
 */
public interface ISpielfeld {
    /**
     * Method for updating the actual Field. 
     * @param x = x Coordinate of Field
     * @param y = y Coordinate of Field
     * @param status = status of Field (enum FieldStatus)
     */
    void updateField(int x, int y, FieldStatus status); 
    
    
}
