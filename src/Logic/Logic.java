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
    private Ship previewShip;
    private FieldStatus[][] WaterField = new FieldStatus[Constant.fieldSize][Constant.fieldSize]; 
    boolean AmZug = true;
    
    public Logic(){
        //Init
        for(int i = 0; i < Constant.fieldSize; i++){
            for(int x = 0; x < Constant.fieldSize; x++){
                WaterField[i][x] = FieldStatus.UNKNOWNAREA;
            }
        }
    }
    
    public void setPlayField(IPlayingField PlayingField){
        this.PlayingFiled = PlayingField;
    }
    
    @Override
    public boolean setPreviewSip(int x, int y, ShipAlignment direction, int lenght){
        if(previewShip != null){        //if already a ship exists delete the existing
            for(ShipFields SF : previewShip.returnShipFields()){
                //Delet the Preview Ship
                PlayingFiled.updateField(SF.getX(), SF.getY(), PlayerField.OWN, FieldStatus.UNUSED);
            }
            for(Ship s : Ships){
                RedrawWholeShip(s);
            }
        }
        //TODO draw new ship
        previewShip = new Ship(x,y,direction,lenght);
        previewShip.setToPreview();
        for(ShipFields SF : previewShip.returnShipFields()){
            for(Ship s : Ships){
                if(s.isCollision(SF.getX(), SF.getY())){
                    SF.setStatus(FieldStatus.COLLISION); //TODO test wether it really changes the ship class!!!!
                }
            }
        }
        RedrawWholeShip(previewShip);
        return true;
    }
    
    @Override
    public void UpdateField(int x, int y, FieldStatus status){
        if(status == FieldStatus.ALLREADYHIT){  //when the soot wasn't accepted
            AmZug = true;
            PlayingFiled.myTurn(AmZug);
        }
        else if(status == FieldStatus.HIT || status == FieldStatus.DESTROYED){
            AmZug = true;
            PlayingFiled.updateField(x, y, PlayerField.ENEMY, status);
            PlayingFiled.myTurn(AmZug);
        }
        else{
            PlayingFiled.updateField(x, y, PlayerField.ENEMY, status);
        }
    }
    
    @Override
    public void setReady(){
        AmZug = true;
        PlayingFiled.myTurn(AmZug);
    }
    
    @Override
    public void gameWin(boolean win){
        PlayingFiled.gameOver(win);
    }
    
    @Override
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
        RedrawWholeShip(newShip);
        Ships.add(newShip); //Add ship to the list
        return true;
    }
    
    @Override
    public boolean shoot(int x, int y){
        if(AmZug){
            AmZug = false;
            Network.sendMoveToEnemy(x, y);
            PlayingFiled.myTurn(AmZug);
            return true;
        }
        return false;
    }
    
    @Override
    public void shootReply(int x, int y, FieldStatus status){
        UpdateField(x,y,status);
    }
    
    @Override
    public FieldStatus shootFromEnemy(int x, int y){
        boolean accepted = false;
        for(Ship s : Ships){
            FieldStatus status = s.shootShip(x, y);
            if(status != FieldStatus.UNKNOWNAREA){
                if(FieldStatus.ALLREADYHIT != status){
                    if(status == FieldStatus.DESTROYED){
                        RedrawWholeShip(s);
                        TestWin();
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
            AmZug = true;                //if he hits water his turn ends and you starts
            PlayingFiled.myTurn(AmZug);
            return FieldStatus.WATER;    //return new Status
        }
        return FieldStatus.ALLREADYHIT; //already hit means not accepted shot
    }
    
    private void RedrawWholeShip(Ship s){
        for(ShipFields SF : s.returnShipFields()){
            //TODO send datas thorugh network in new function
            PlayingFiled.updateField(SF.getX(), SF.getY(), PlayerField.OWN, SF.getStatus());
        }
    }
   
    private void TestWin(){
        boolean fertig = true;
        for(Ship s : Ships){
            if(!s.isDestroyed())
                fertig = false;
        }
        if(fertig){
            Network.sendGameWin(true);
            PlayingFiled.gameOver(false);
        }
    }

    @Override
    public void setFirstPlayer(boolean firstPlayer) {
        AmZug = firstPlayer;
        PlayingFiled.myTurn(AmZug);
    }
}
