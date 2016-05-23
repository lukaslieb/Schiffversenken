package Gui;


import Datatypes.FieldStatus;
import javax.swing.ImageIcon;
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
    private ImageIcon img = new ImageIcon();

    public Field(int x, int y) {
        this.button = new JButton();
        setImg(this.button, FieldStatus.UNUSED);
        this.x = x;
        this.y = y;
    }
    
    public Field(){
        
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public JButton getButton() {
        return this.button;
    }
    
    public void setImg(JButton j,FieldStatus f){
        j.setIcon(new javax.swing.ImageIcon(getClass().getResource(FieldStatus.getPic(f))));
    }
}
