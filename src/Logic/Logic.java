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
import Gui.Schiffversenken;
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

    public IEnemy getNetwork() {
        return Network;
    }

    public void setNetwork(IEnemy Network) {
        this.Network = Network;
    }

    public ArrayList<Ship> getShips() {
        return Ships;
    }

    public void setShips(ArrayList<Ship> Ships) {
        this.Ships = Ships;
    }

    public FieldStatus[][] getWaterField() {
        return WaterField;
    }

    public void setWaterField(FieldStatus[][] WaterField) {
        this.WaterField = WaterField;
    }

    public boolean isAmZug() {
        return AmZug;
    }

    public void setAmZug(boolean AmZug) {
        this.AmZug = AmZug;
    }
    
    
    
    public void setPlayField(IPlayingField PlayingField){
        this.PlayingFiled = PlayingField;
    }
    
    @Override
    public boolean setPreviewSip(int x, int y, ShipAlignment direction, int lenght){
        if(ConnectionsDefined()){
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
        return false;
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
        if(ConnectionsDefined()){
            Ship newShip = new Ship(x, y, direction, lenght);
            //When the Ships are not empty do collision detection
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
        return false;
    }
    
    @Override
    public boolean shoot(int x, int y){
        if(isAmZug()){
            setAmZug(false);
            Network.sendMoveToEnemy(x, y);
            PlayingFiled.myTurn(AmZug);
            return true;
        }
        return false;
    }
    
    @Override
    public void shootReply(int x, int y, FieldStatus status){
        UpdateField(x,y,status);
        if(status == FieldStatus.HIT || status == FieldStatus.DESTROYED){
            Schiffversenken.soundHit();
            setAmZug(true);
            PlayingFiled.updateField(x, y, PlayerField.ENEMY, status);
        }
        else if(status==FieldStatus.WATER){
            Schiffversenken.soundWater();
        }
        else if(status == FieldStatus.ALLREADYHIT){  //when the soot wasn't accepted
            setAmZug(true);
        }
        else{
            PlayingFiled.updateField(x, y, PlayerField.ENEMY, status);
        }
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
                        RedrawEnemyShip(s);
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
            PlayingFiled.myTurn(AmZug);
            setAmZug(true);                //if he hits water his turn ends and you starts
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
    
    private void RedrawEnemyShip(Ship s){
        for(ShipFields SF : s.returnShipFields()){
            Network.UpdateEnemyField(SF.getX(), SF.getY(), SF.getStatus());
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
    
    private boolean ConnectionsDefined(){
        boolean isconnected = true;
        if(Network == null){
            isconnected = false;
        }
        if(PlayingFiled == null){
            isconnected = false;
        }
        return isconnected;
    }
}
