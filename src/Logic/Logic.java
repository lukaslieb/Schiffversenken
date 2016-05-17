/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Datatypes.FieldStatus;
import Datatypes.ShipAlignment;
import Datatypes.Constant;
import Interface.ILogic;
import Interface.ILogicEnemy;
import java.util.ArrayList;


/**
 *
 * @author Daniel
 */
public class Logic implements ILogic,ILogicEnemy{
    private ArrayList<Ship> Ships = new ArrayList<Ship>();
    private FieldStatus[][] WaterField = new FieldStatus[Constant.fieldSize][Constant.fieldSize]; 
    
    public void Logic(){
        //Init
        for(FieldStatus[] s : WaterField){
            for(FieldStatus f : s){
                f = FieldStatus.WATER;
            }
        }
    }
    
    @Override
    public boolean setShip(int x, int y, ShipAlignment direction, int lenght){
        Ship newShip = new Ship(x, y, direction, lenght);
        
    }
    
    @Override
    public boolean shoot(int x, int y){
        
    }
    
    @Override
    public FieldStatus shootFromEnemy(int x, int y){
        
    }
}
