/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

/**
 * This interface define the Methods how communicate with the logic.
 * @author Lukas
 */
public interface ILogik {
    /**
     * This method tells the logic on witch field the player has shoot.
     * @param x = x coordinate of the field
     * @param y = y coordinate of the field
     * @return true if the shoot was acceptet from the logic
     */
    boolean shoot(int x, int y);
}
