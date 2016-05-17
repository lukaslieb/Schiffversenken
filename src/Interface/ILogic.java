/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Datatypes.ShipAlignment;
import Logic.Ship;

/**
 * This interface define the Methods how communicate with the logic.
 * @author Lukas
 */
public interface ILogic {
    /**
     * This method tells the logic on witch field the player has shoot.
     * @param x = x coordinate of the field
     * @param y = y coordinate of the field
     * @return true if the shoot was acceptet from the logic
     */
    boolean shoot(int x, int y);
    
    /**
     * Set the ship to the coordinates witch the player choose.
     * @param ship
     * @param direction 1=waagerecht, 2=senkrecht
     * @return true if the ship was acceptet at this place.
     */
    boolean setShip(int x, int y, ShipAlignment direction, int length);
}
