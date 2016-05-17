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
    private ShipAlignment align;
    private int length;
    
    
    public Ship(int x, int y, ShipAlignment align, int length){
        this.x = x;
        this.y = y;
        this.align = align;
        this.length = length;
        CreateShip();
    }
    
    private void CreateShip(){
        switch(align){
            case VERTICAL:
                break;
            case HORIZONTAL:
                break;
        }
    }
}
