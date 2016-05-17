/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
import static Datatypes.Constant.fieldSize;
import Datatypes.FieldStatus;
import Datatypes.ShipAlignment;

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
    
    public Ship(int x, int y, ShipAlignment align, int length){
        this.x = x;
        this.y = y;
        this.align = align;
        this.length = length;
        checkPos();
        CreateShip();
        
    }

    
    
    private void checkPos(){
        switch(align){
            case VERTICAL:
                if(!((y+length) < fieldSize)){
                    y=fieldSize-length-1;
                }
                break;
            case HORIZONTAL:
                if(!((x+length) < fieldSize)){
                    x=fieldSize-length-1;
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
        //TODO abfrage ob kollision
        if(x== placement[0].getX()){
            for(int pos=0; pos<length; pos++){
                if(y==placement[pos].getY()){
                    return true;
                }
            }
        }
    }
    public FieldStatus shootShip(int x,int y){
        //TODO allreadyhit, hit
        
        return FieldStatus.UNKNOWNAREA;
    }
    public boolean isDestroyed(){
        
        //TODO true, false
    }
    public ShipFields[] returnShipFields (){
        return placement;
    }
}
