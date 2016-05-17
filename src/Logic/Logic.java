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
        //TODO test wether ship is placeable
        //TODO add ship to list
        //TODO return wether placeable
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
            // TODO auf jedes schiff schiessen
            // TODO wenn getroffen, accepted = true;
        }
        if(!accepted){
            if(WaterField[x][y] == FieldStatus.UNKNOWNAREA){
                WaterField[x][y] = FieldStatus.WATER;
                accepted = true;
                PlayingFiled.updateField(x, y, PlayerField.OWN, FieldStatus.WATER);
                return FieldStatus.WATER;
            }
        }
        return FieldStatus.UNKNOWNAREA; //already hit means not accepted shot
    }
}
