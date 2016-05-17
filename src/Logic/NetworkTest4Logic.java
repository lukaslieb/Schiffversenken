/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Datatypes.FieldStatus;
import Interface.IEnemy;
import Interface.ILogicEnemy;



/**
 *
 * @author Daniel
 */
public class NetworkTest4Logic implements IEnemy{
    ILogicEnemy gegner;
    
    public void setIlogic(ILogicEnemy LogicEnemy){
        this.gegner = LogicEnemy;
    }
    
    @Override
    public FieldStatus sendMoveToEnemy(int x, int y){
        return gegner.shootFromEnemy(x, y);
    }
    
    /**
     * This function sends messages to the enemy
     * @param msg = String Message
     */
    @Override
    public void comWithEnemy(String msg){
        
    }
}
