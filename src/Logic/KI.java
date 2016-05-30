/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Datatypes.FieldStatus;
import Datatypes.ShipAlignment;
import Interface.IEnemy;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author felix
 */
public class KI extends Logic implements IEnemy{
    
    private Random rand;
    private Logic logic;
    
    
    public KI(){
        super();
        rand = new Random();
        randomShip();
    }
    
    public void setLogic(Logic logic){
        this.logic = logic;
    }
    
    @Override
    public void setAmZug(boolean AmZug){
        this.AmZug = AmZug;
        if(AmZug){
            randomShoot();
            
        }
    }
    
    @Override
    public boolean setShip(int x, int y, ShipAlignment direction, int lenght){
        Ship newShip = new Ship(x, y, direction, lenght);
        //When the Ships are not empty do collision detection
        if(!getShips().isEmpty()){
            for(ShipFields SF : newShip.returnShipFields()){
                for(Ship s : getShips()){
                    if(s.isCollision(SF.getX(), SF.getY())){
                        return false; //Collison detected
                    }
                }
            }
        }
        getShips().add(newShip); //Add ship to the list
        return true;
    }
    
   
    
    @Override
    public FieldStatus shootFromEnemy(int x, int y){
        boolean accepted = false;
        for(Ship s : getShips()){
            FieldStatus status = s.shootShip(x, y);
            if(status != FieldStatus.UNKNOWNAREA){
                if(FieldStatus.ALLREADYHIT != status){
                    if(status == FieldStatus.DESTROYED){
                        //UpdateWholeShip(s);
                        TestWin();
                    }
                    //PlayingFiled.updateField(x, y, PlayerField.OWN, status);
                }
                return status;          //Return new Fieldstatus
            }
        }
        if(getWaterField()[x][y] == FieldStatus.UNKNOWNAREA){
            getWaterField()[x][y] = FieldStatus.WATER;
            accepted = true;
            //PlayingFiled.updateField(x, y, PlayerField.OWN, FieldStatus.WATER);
            setAmZug(true);                //if he hits water his turn ends and you starts
            return FieldStatus.WATER;    //return new Status
        }
        return FieldStatus.ALLREADYHIT; //already hit means not accepted shot
    }
    private void randomShip (){
        this.setShip(0, 0, ShipAlignment.VERTICAL, 4);
        this.setShip(2, 0, ShipAlignment.VERTICAL, 3);
        this.setShip(4, 0, ShipAlignment.VERTICAL, 2);
        this.setShip(6, 0, ShipAlignment.VERTICAL, 2);
    }
    
    @Override
    public void shootReply(int x, int y, FieldStatus status){
        if(status == FieldStatus.ALLREADYHIT){  //when the soot wasn't accepted
            setAmZug(true);
        }
        else if(status == FieldStatus.HIT ||status == FieldStatus.DESTROYED){
            setAmZug(true);
            //PlayingFiled.updateField(x, y, PlayerField.ENEMY, status);
        }
        else{
        }
    }
    
    public void randomShoot(){
        int x,y;
        x = rand.nextInt(10);
        y = rand.nextInt(10);        
        shootReply(x,y,logic.shootFromEnemy(x, y));
    }
    
    private void TestWin(){
        boolean fertig = true;
        for(Ship s : getShips()){
            if(!s.isDestroyed())
                fertig = false;
        }
        if(fertig){
            logic.gameWin(true);
            //PlayingFiled.gameOver(false);
        }
    }
    
    @Override
    public void sendMoveToEnemy(int x, int y){
        logic.shootReply(x,y,this.shootFromEnemy(x,y));  
    }
    
    @Override
    public void comWithEnemy(String msg){

    }
    
    @Override
    public void sendGameWin(boolean win){
        
    }
    @Override
    public void sendFirstPlayer(boolean firstPlayer){
        
    }
    @Override
    public void UpdateEnemyField(int x, int y, FieldStatus status){
        
    }
}
