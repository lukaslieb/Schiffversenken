/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Datatypes.FieldStatus;
import Datatypes.ShipAlignment;
import Interface.IEnemy;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author felix
 */
public class KI extends Logic implements IEnemy{
    
    private Random rand;
    private Logic logic;
    private ArrayList<HittedField> HittedFields = new ArrayList<HittedField>();
    private ArrayList<Ship> DestroyedShips = new ArrayList<Ship>();
    
    
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
                        RedrawEnemyShip(s);
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
        for(int size : Datatypes.Constant.ships){
            boolean Shipset = false;
            while(!Shipset){
                int x,y;
                x = rand.nextInt(10);
                y = rand.nextInt(10);
                boolean ausrichtung = rand.nextBoolean();
                ShipAlignment SA = ShipAlignment.HORIZONTAL;
                if(ausrichtung){
                    SA = ShipAlignment.VERTICAL;
                }
                Shipset = this.setShip(x, y, SA, size);
            }
        }
        /*
        this.setShip(0, 0, ShipAlignment.VERTICAL, 4);
        this.setShip(2, 0, ShipAlignment.VERTICAL, 3);
        this.setShip(4, 0, ShipAlignment.VERTICAL, 2);
        this.setShip(6, 0, ShipAlignment.VERTICAL, 2);*/
    }
    
    @Override
    public void shootReply(int x, int y, FieldStatus status){
        if(status == FieldStatus.ALLREADYHIT){  //when the soot wasn't accepted
            setAmZug(true);
        }
        else if(status == FieldStatus.HIT){
            HittedField HF = new HittedField(x, y);
            HittedFields.add(HF);
            
            setAmZug(true);
            //PlayingFiled.updateField(x, y, PlayerField.ENEMY, status);
        }
        else if(status == FieldStatus.DESTROYED){
            HittedField HFn = new HittedField(x, y);
            HittedFields.add(HFn);
            int xc=10;
            int yc=10;
            ShipAlignment SA = ShipAlignment.HORIZONTAL;
            for(HittedField HF : HittedFields){
                if(xc == HF.x){
                    SA = ShipAlignment.VERTICAL;
                }
                if(xc > HF.x){
                    xc = HF.x;
                }
                if(yc > HF.y){
                    yc = HF.y;
                }
            }
            DestroyedShips.add(new Ship(xc,yc,SA,HittedFields.size()));
            
            HittedFields.clear();
            
            setAmZug(true);
        }
    }
    
    public void randomShoot(){
        int x,y;
        x=0;
        y=0;
        if(HittedFields.isEmpty()){
            x = rand.nextInt(10);
            y = rand.nextInt(10);      
        }
        else if(HittedFields.size() == 1){
                x = HittedFields.get(0).x;
                y = HittedFields.get(0).y;
            if(!HittedFields.get(0).north){
                HittedFields.get(0).north = true;
                y = HittedFields.get(0).y-1;
            }
            else if(!HittedFields.get(0).east){
                HittedFields.get(0).east = true;
                x = HittedFields.get(0).x+1;
            }
            else if(!HittedFields.get(0).south){
                HittedFields.get(0).south = true;
                y = HittedFields.get(0).y+1;
            }
            else if(!HittedFields.get(0).west){
                HittedFields.get(0).west = true;
                x = HittedFields.get(0).x-1;
            }
            else{
                System.out.println("FEHLER ;P");
            }
        }
        else{
            boolean wechsel = rand.nextBoolean();
            if(wechsel){
                x = 10;
                y = 10;
                ShipAlignment SA = ShipAlignment.HORIZONTAL;
                for(HittedField HF : HittedFields){                    
                    if(x == HF.x){
                        SA = ShipAlignment.VERTICAL;
                    }
                    if(x > HF.x){
                        x = HF.x;
                    }
                    if(y > HF.y){
                        y = HF.y;
                    }
                }
                
                if(SA == ShipAlignment.HORIZONTAL){
                    if(x > 0){
                        x -= 1;
                    }
                }
                else{
                    if(y > 0){
                        y -= 1;
                    }
                }
            }
            else{
                x=-1;
                y=-1;
                ShipAlignment SA = ShipAlignment.HORIZONTAL;
                for(HittedField HF : HittedFields){
                    if(x == HF.x){
                        SA = ShipAlignment.VERTICAL;
                    }
                    if(x < HF.x){
                        x = HF.x;
                    }
                    if(y < HF.y){
                        y = HF.y;
                    }
                }
                
                if(SA == ShipAlignment.HORIZONTAL){
                    if(x < Datatypes.Constant.fieldSize-1){
                        x += 1;
                    }
                }
                else{
                    if(y < Datatypes.Constant.fieldSize-1){
                        y += 1;
                    }
                }
            }
        }
        if(!DestroyedShips.isEmpty()){
            boolean nochmal = false;
            for(Ship s : DestroyedShips){
                if(s.isCollision(x, y)){
                    nochmal = true;
                }
            }
            if(nochmal){
                setAmZug(true);
            }
            else{
                shootReply(x,y,logic.shootFromEnemy(x, y));
            }
        }
        else{
            shootReply(x,y,logic.shootFromEnemy(x, y));
        }
    }
    
    private void RedrawEnemyShip(Ship s){
        for(ShipFields SF : s.returnShipFields()){
            logic.UpdateField(SF.getX(), SF.getY(), SF.getStatus());
        }
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
