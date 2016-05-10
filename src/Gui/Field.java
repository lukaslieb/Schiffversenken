package Gui;

import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sandro
 */
public class Field {
    
    private JButton button;
    private int x;
    private int y;
    private boolean field1;
    
    public Field (int x, int y, boolean field1){
        this.button = new JButton();
        this.x = x;
        this.y = y;
        this.field1 = field1;
        
    }
    
    public int getX(){
        return this.x+1;
    }
    public int getY(){
        return this.y+1;
    }
    
    public JButton getButton(){
        return this.button;
    }
    
}
