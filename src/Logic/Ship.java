/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
import static Datatypes.Constant.fieldSize;
import Datatypes.FieldStatus;
import Datatypes.ShipAlignment;
import java.util.HashSet;

/**
 * Class witch define the Ship
 * @author Lukas
 */
public class Ship {
    private int x;
    private int y;
    private ShipAlignment align;
    private int length;
    private ShipFields[] placement;
    private boolean Destroyed;
    
    public Ship(int x, int y, ShipAlignment align, int length){
        Destroyed = false;
        this.x = x;
        this.y = y;
        this.align = align;
        this.length = length;
        checkPos();
        CreateShip();
    } 
    
    public void setToPreview(){
        for(ShipFields SF : placement){
            SF.setStatus(FieldStatus.PREVIEW);
        }
    }
    
    private void checkPos(){
        switch(align){
            case VERTICAL:
                if(!((y+length) <= fieldSize)){
                    y=fieldSize-length;
                }
                break;
            case HORIZONTAL:
                if(!((x+length) <= fieldSize)){
                    x=fieldSize-length;
                }
                break;
        }
    } 
            
    private void CreateShip(){
        
        switch(align){
            case VERTICAL:
                placement= new ShipFields[length];
                for(int i=0; i<length; i++)
                {
                    placement[i]=new ShipFields(x,y+i,FieldStatus.SHIP);
                }
                break;
            case HORIZONTAL:
                placement= new ShipFields[length];
                for(int i=0; i<length; i++)
                {
                    placement[i]=new ShipFields(x+i,y,FieldStatus.SHIP);
                }
                break;
        }
        
               
       
    }
    public boolean isCollision(int x, int y){
        //TODO abfrage ob kollision ausbessern evtl.
        for(ShipFields SF : placement){
            if(SF.getX()-1 <= x && x <= SF.getX()+1){
                if(SF.getY()-1 <= y && y <= SF.getY()+1){
                    return true;
                }
            }
        }
        return false;
    }
    public FieldStatus shootShip(int x,int y){
        for(ShipFields SF : placement){
            if((SF.getX() == x) &&(SF.getY() == y)){   //èbereinstimmung gefunden
                if(SF.getStatus() == FieldStatus.SHIP){
                    SF.setStatus(FieldStatus.HIT);
                    TestDestroyedship();
                    return SF.getStatus();
                }
                else{                                  //bereits getroffen
                    return FieldStatus.ALLREADYHIT;
                }
            }
        }
        return FieldStatus.UNKNOWNAREA;
    }
    
    private void TestDestroyedship(){
        boolean destroyed = true;
        for(ShipFields SF : placement){
            if((SF.getStatus() != FieldStatus.HIT) && (SF.getStatus() != FieldStatus.DESTROYED)){
                destroyed = false;
            }
        }
        if(destroyed == true){
            for(ShipFields SF : placement){
                SF.setStatus(FieldStatus.DESTROYED);
            }
        }
        this.Destroyed = destroyed;
    }
    
    public boolean isDestroyed(){
        return Destroyed;
    }
    public ShipFields[] returnShipFields (){
        return placement;
    }
}
