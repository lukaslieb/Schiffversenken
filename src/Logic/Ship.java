/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
import Datatypes.FieldStatus;
import Datatypes.ShipAlignment;

/**
 * Class witch define the Ship
 * @author Lukas
 */
public class Ship {
    private int x;
    private int y;
    private FieldStatus status;
    private ShipAlignment align;
    
    public Ship(int x, int y, FieldStatus status, ShipAlignment align){
        this.x = x;
        this.y = y;
        this.status = status;
        this.align = align;
    }
}
