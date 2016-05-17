/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Datatypes.FieldStatus;
import Datatypes.ShipAlignment;
import Datatypes.Constant;
import Datatypes.PlayerField;
import Interface.IEnemy;
import Interface.ILogic;
import Interface.ILogicEnemy;
import Interface.IPlayingField;
import java.util.ArrayList;


/**
 *
 * @author Daniel
 */
public class Logic implements ILogic,ILogicEnemy{
    private IPlayingField PlayingFiled;
    private IEnemy Network;
    private ArrayList<Ship> Ships = new ArrayList<Ship>();
    private FieldStatus[][] WaterField = new FieldStatus[Constant.fieldSize][Constant.fieldSize]; 
    
    public void Logic(){
        //Init
        for(FieldStatus[] s : WaterField){
            for(FieldStatus f : s){
                f = FieldStatus.UNKNOWNAREA;
            }
        }
    }
    
    public void setPlayField(IPlayingField PlayingField){
        this.PlayingFiled = PlayingField;
    }
    
    public void SetNetworkconnection(IEnemy Network){
        this.Network = Network;
    }
    
    @Override
    public boolean setShip(int x, int y, ShipAlignment direction, int lenght){
        Ship newShip = new Ship(x, y, direction, lenght);
        //When the Sips are not empty do collision detection
        if(!Ships.isEmpty()){
            for(ShipFields SF : newShip.returnShipFields()){
                for(Ship s : Ships){
                    if(s.isCollision(SF.getX(), SF.getY())){
                        return false; //Collison detected
                    }
                }
            }
        }
        UpdateWholeShip(newShip);
        Ships.add(newShip); //Add ship to the list
        return true;
    }
    
    @Override
    public boolean shoot(int x, int y){
        FieldStatus shootfield = Network.sendMoveToEnemy(x, y);
        if(shootfield == FieldStatus.UNKNOWNAREA){  //when the soot wasn't accepted
            return false;
        }
        PlayingFiled.updateField(x, y, PlayerField.ENEMY, shootfield);
        return true;
    }
    
    @Override
    public FieldStatus shootFromEnemy(int x, int y){
        boolean accepted = false;
        for(Ship s : Ships){
            FieldStatus status = s.shootShip(x, y);
            if(status != FieldStatus.UNKNOWNAREA){
                if(FieldStatus.ALLREADYHIT != status){
                    if(status == FieldStatus.DESTROYED){
                        UpdateWholeShip(s);
                    }
                    PlayingFiled.updateField(x, y, PlayerField.OWN, status);
                }
                return status;          //Return new Fieldstatus
            }
        }
        if(WaterField[x][y] == FieldStatus.UNKNOWNAREA){
            WaterField[x][y] = FieldStatus.WATER;
            accepted = true;
            PlayingFiled.updateField(x, y, PlayerField.OWN, FieldStatus.WATER);
            return FieldStatus.WATER;    //return new Status
        }
        return FieldStatus.ALLREADYHIT; //already hit means not accepted shot
    }
    
    private void UpdateWholeShip(Ship s){
        for(ShipFields SF : s.returnShipFields()){
            PlayingFiled.updateField(SF.getX(), SF.getY(), PlayerField.OWN, SF.getStatus());
        }
    }
}
