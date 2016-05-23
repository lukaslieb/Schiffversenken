/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Datatypes.ShipAlignment;
import Interface.IEnemy;
import java.util.Random;

/**
 *
 * @author felix
 */
public class KI extends Logic implements IEnemy{
    
    private Random rand;
    public KI(){
        super();
        rand = new Random();
    }

    public void randomShip (){
        this.setShip(0, 0, ShipAlignment.VERTICAL, 4);
        this.setShip(2, 0, ShipAlignment.VERTICAL, 3);
        this.setShip(4, 0, ShipAlignment.VERTICAL, 2);
        this.setShip(6, 0, ShipAlignment.VERTICAL, 2);
    }
    
    public void randomShoot(){
        
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        this.shoot(x,y);
    }
    
    
    @Override
    public void sendMoveToEnemy(int x, int y){
        
        this.shootFromEnemy(x,y);
        
    }
    
    @Override
    public void comWithEnemy(String msg){

    }
}
