/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author Daniel
 */
public class HittedField {
    public int x;
    public int y;
    public boolean north;
    public boolean south;
    public boolean west;
    public boolean east;
    
    public HittedField(int x, int y){
        this.x = x;
        this.y = y;
        if(y != 0){
            north = false;
        }
        else{
            north = true;
        }
        if(y == Datatypes.Constant.fieldSize-1){
            south = true;
        }
        else{
            south = false;
        }
        if(x != 0){
            west = false;
        }
        else{
            west = true;
        }
        if(x == Datatypes.Constant.fieldSize-1){
            east = true;
        }
        else{
            east = false;
        }
        
        
        
        
    }
}
